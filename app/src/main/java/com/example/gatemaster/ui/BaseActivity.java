package com.example.gatemaster.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import app.GateMasterApplication;
import busevent.BusEventDefault;
import busevent.CheckOutBusEvent;
import busevent.LoginBusEvent;
import busevent.VisiteeDetails;
import datamodel.LoginDataModel;
import datamodel.PostCheckIn;
import datamodel.PostCheckOut;
import datamodel.ResponseCheckIn;
import datamodel.ResponseCheckOut;
import datamodel.ResponseVisiteeDetails;
import datamodel.UserDetailRequest;
import db.DatabaseConnection;
import model.ActiveEntriesResponse;
import model.LogoutResponse;
import model.VisitorDetailsResponse;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit.GateApi;
import retrofit.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constant;
import utils.ProgressWheel;
import utils.SharedPref;
import utils.Util;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BASEACTIVITY";
    public DatabaseConnection databaseConnection;
    @Inject
    protected SharedPref sharedPref;

    public String baseUrl;

    private View progressLayout,toplay;
    private ImageView profileIcon;
    private ProgressWheel progresswheel;
    protected LayoutInflater inflator = null;

    public GateApi gateApi;

    public TextView homePageTitle;

    public LayoutInflater getInflator() {
        return inflator;
    }

    protected View inflateView(int layoutId,String title,boolean isToolbar) {
        ViewGroup v = (ViewGroup) findViewById(R.id.content);
        homePageTitle.setText(title);
        View view = getInflator().inflate(layoutId, v);
        if(isToolbar){
            toplay.setVisibility(View.VISIBLE);
        }else {
            toplay.setVisibility(View.GONE);
        }
        return view;
    }
    public SharedPref getSharedPreferences() {
        return sharedPref;
    }



    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GateMasterApplication.getComponent().injects(this);
        inflator = LayoutInflater.from(this);
        setContentView(R.layout.activity_main);
        databaseConnection = DatabaseConnection.getInstance(this);
        progressLayout = (View) findViewById(R.id.progressLayout);
        progresswheel = (ProgressWheel) findViewById(R.id.progresswheel);
        toplay = (View)findViewById(R.id.toplay);
        homePageTitle = (TextView)findViewById(R.id.homePageTitle);
        baseUrl = sharedPref.getString("baseUrl");
        if (baseUrl.length() > 0) {
            Constant.BASE_URL = baseUrl;
        }
        if (Constant.BASE_URL.length() > 0) {
            gateApi = ServiceGenerator.createService(GateApi.class, "", "");
        }
        profileIcon = (ImageView)findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaseActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                // Util.pushNext(BaseActivity.this, ProfileActivity.class);

            }
        });
        create(savedInstanceState);
    }

    protected abstract void create(Bundle bundle);

    @Override
    public void onBackPressed() {
        if (BaseActivity.this instanceof HomeActivity) {
            Util.showAlert(this, "Are you sure want to exit the application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        finishAffinity();
                    } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.dismiss();
                    }
                }
            });

        } else {
            Util.pushNext(this, HomeActivity.class);
        }
    }

    public void apicall() {
    }


    public void executeLoginApi(String userID, String password) {

        UserDetailRequest userDetailRequest = new UserDetailRequest();
        userDetailRequest.setEmployeeId(userID);
        userDetailRequest.setEmployeePin(password);

        Call<LoginDataModel> loginDataModelCall = gateApi.userValidate(userDetailRequest);

        loginDataModelCall.enqueue(new Callback<LoginDataModel>() {
            boolean isSuccess;
            int active = 0;

            @Override
            public void onResponse(Call<LoginDataModel> call, Response<LoginDataModel> response) {
                if (response.code() == 200) {
                    Log.d("BASE-URL", "LoginApi: ==============>url - "
                            + call.request().url() + " ===============>" + call.request().body().toString());
                    if (response.body().getTitle().equalsIgnoreCase("OK")) {
                        isSuccess = true;
                    } else {
                        isSuccess = false;
                    }
                } else {
                    isSuccess = false;
                }

                if (isSuccess) {
                    databaseConnection.deleteTable("UserDetail");
                    active = 1;
                    sharedPref.save("userid", response.body().getResponseData().getUser().getEmployeeId());
                    sharedPref.save("useremail", response.body().getResponseData().getUser().getEmail());
                    sharedPref.save("userph", response.body().getResponseData().getUser().getMobile());
                    sharedPref.save("username", response.body().getResponseData().getUser().getName());
                    databaseConnection.insertUserDetails(response.body().getResponseData().getUser().getEmployeeId(), response.body().getResponseData().getUser().getName(), response.body().getResponseData().getUser().getEmail(), response.body().getResponseData().getUser().getMobile());
//                    Constant.PREF_AUTH_TOKEN = response.body().getResponseData().getAccessToken().toString();
                    sharedPref.save(Constant.PREF_AUTH_TOKEN, response.body().getResponseData().getAccessToken().toString());
                    sharedPref.saveBool("isloggedin",true);
                    sharedPref.save("lastlogin",Util.getcurrenttime());
                    sharedPref.save("pwd",password);
                    Constant.Exp_Time = response.body().getResponseData().getExpiresIn() / 3600;
                    EventBus.getDefault().post(new LoginBusEvent("YES", active, response.body().getTitle()));
                } else {
                    active = 0;
                    EventBus.getDefault().post(new LoginBusEvent("YES", active, response.message()));
                }
            }

            @Override
            public void onFailure(Call<LoginDataModel> call, Throwable t) {
                isSuccess = false;
                active = 0;
                EventBus.getDefault().post(new LoginBusEvent("NO", active, t.getMessage()));
            }

            ;

        });
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(BusEventDefault event) {

    }

    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
        progresswheel.spin();
        progressLayout.setEnabled(true);
        progressLayout.setClickable(true);
    }

    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
        progresswheel.stopSpinning();
        progressLayout.setEnabled(false);
        progressLayout.setClickable(false);
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    public long gettokentimediff(){
        String lastlogintime = sharedPref.getString("lastlogin");
        String currenttime = Util.getcurrenttime();

        long diff = Util.getDifferenceInHours(lastlogintime,currenttime,Constant.Date_Format);

        return diff;
    }

    public void postCheckIn(String gateid, String name, String address, String purpose, String vehiclenum, String visittype, String mob) {
        PostCheckIn postCheckIn = new PostCheckIn();
        postCheckIn.setGateId(gateid);
        postCheckIn.setPurpose(purpose);
        postCheckIn.setVisitorName(name);
        postCheckIn.setVisitorAddress(address);
        postCheckIn.setVehicleRegistrationNumber(vehiclenum);
        postCheckIn.setVisitorType(visittype);
        postCheckIn.setVisitorMobile(mob);

        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<ResponseCheckIn> call = gateApi.postcheckin(token, postCheckIn);

        call.enqueue(new Callback<ResponseCheckIn>() {
            @Override
            public void onResponse(Call<ResponseCheckIn> call, Response<ResponseCheckIn> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTitle().equalsIgnoreCase("Created")) {
                        EventBus.getDefault().post(new BusEventDefault(response.body().getTitle(), true));
                    } else {
                        EventBus.getDefault().post(new BusEventDefault(response.body().getMessage(), false));
                    }
                } else {
                    String message = "";
                    try {
                        message = getErrorFromResponse(response.errorBody().string());
                    } catch (IOException e) {
                        message = e.getMessage().toString();
                        throw new RuntimeException(e);
                    }
                    EventBus.getDefault().post(new BusEventDefault(message, false));
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckIn> call, Throwable t) {
                EventBus.getDefault().post(new BusEventDefault("Error in connecting with Server.", false));
            }
        });
    }

    public String getErrorFromResponse(String response) {
        String errorBody = response, message = "";
        try {
            JSONObject jsonObject = new JSONObject(errorBody);
            message = jsonObject.optString("message", "No message found");
        } catch (JSONException e) {
            message = "Unable to get Error Message";
            throw new RuntimeException(e);
        }
        return message;
    }


    public void postcheckoutapi(String gatereqid, int pos) {
        PostCheckOut postCheckOut = new PostCheckOut();
        postCheckOut.setGateEntriesRequestId(gatereqid);

        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<ResponseCheckOut> call = gateApi.postcheckout(token, postCheckOut);

        call.enqueue(new Callback<ResponseCheckOut>() {
            @Override
            public void onResponse(Call<ResponseCheckOut> call, Response<ResponseCheckOut> response) {
                if (response.isSuccessful()) {
                    EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Success, pos,response.body().getMessage()));
                } else {
                    try {
                        EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Failed, pos,getErrorFromResponse(response.errorBody().string())));
                    } catch (IOException e) {
                        EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Failed, pos,e.getMessage()));
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckOut> call, Throwable t) {
                EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Failed, pos,t.getMessage().toString()));
            }
        });
    }


    public void getvisitingdetails(String phnnum) {
        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<ResponseVisiteeDetails> call = gateApi.getVisiteeDetails(token, phnnum);

        call.enqueue(new Callback<ResponseVisiteeDetails>() {

            @Override
            public void onResponse(Call<ResponseVisiteeDetails> call, Response<ResponseVisiteeDetails> response) {
                if (response.code() == 200) {
                    if (response.body().getTitle().equalsIgnoreCase("ok")) {
                        EventBus.getDefault().post(new VisiteeDetails(response.body().getResponseData().getName(), response.body().getResponseData().getAddress(), true));
                    } else {
                        EventBus.getDefault().post(new VisiteeDetails("", "", false));
                    }
                } else {
                    EventBus.getDefault().post(new VisiteeDetails("", "", false));
                }
            }

            @Override
            public void onFailure(Call<ResponseVisiteeDetails> call, Throwable t) {
                EventBus.getDefault().post(new VisiteeDetails("", "", false));
            }
        });

    }

    public void getActiveEntries() {
        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<ActiveEntriesResponse> call = gateApi.getActiveEntries(token);

        call.enqueue(new Callback<ActiveEntriesResponse>() {
            @Override
            public void onResponse(Call<ActiveEntriesResponse> call, Response<ActiveEntriesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ActiveEntriesResponse data = response.body();

                    // Check the statusCode from the response
                    if ("200".equals(data.getStatusCode())) {
                        databaseConnection.deleteTable("GateEntries");
                        databaseConnection.insertGateEntries(data);
                        Log.d("getActiveentries==========", data.toString());
                        EventBus.getDefault().post(new BusEventDefault("ActiveEntries", true));
                        hideProgress();
                    } else {
                        // Handle unexpected status code (e.g., not 200)
                        EventBus.getDefault().post(new BusEventDefault("ActiveEntries", false));
                        Log.e("getActiveentries", "Unexpected status code: " + data.getStatusCode());
                    }
                } else {
                    // Handle error case when response is not successful
                    EventBus.getDefault().post(new BusEventDefault("ActiveEntries", false));
                    Log.e("getActiveentries", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ActiveEntriesResponse> call, Throwable t) {
                // Handle failure
                Log.e("getActiveentries", "Request failed: " + t.getMessage());
                EventBus.getDefault().post(new BusEventDefault("ActiveEntries", false));
            }
        });
    }

    public void logoutUser() {
        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<LogoutResponse> call = gateApi.logout("Bearer " + token);

        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LogoutResponse logoutResponse = response.body();
                    Log.d("Logout", "Logout message: " + logoutResponse.getMessage());
                    // Handle successful logout
                    databaseConnection.clearAllTables();
                    finishAffinity();
                    sharedPref.saveBool("isloggedin",false);
                    Util.pushNext(BaseActivity.this,LoginActivity.class);
                } else {
                    // Handle logout failure
                    Log.d("Logout", "Logout failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                // Handle request failure
                Log.d("Logout", "Logout error: " + t.getMessage());
            }
        });
    }



/*
    public void fetchVisitorDetails(String mobileNumber) {
        String authToken = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);

        Call<VisitorDetailsResponse> call = gateApi.getVisitorDetails(authToken, mobileNumber);

        call.enqueue(new Callback<VisitorDetailsResponse>() {
            @Override
            public void onResponse(Call<VisitorDetailsResponse> call, Response<VisitorDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VisitorDetailsResponse visitorDetails = response.body();
                    Log.d("Visitor Details", "Name: " + visitorDetails.getResponseData().getName() +
                            ", Address: " + visitorDetails.getResponseData().getAddress());
                } else {
                    Log.e("Visitor Details", "Failed - Code: " + response.code() +
                            ", Message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<VisitorDetailsResponse> call, Throwable t) {
                Log.e("Visitor Details", "Network Error: " + t.getMessage());
            }
        });
    }
*/


}
