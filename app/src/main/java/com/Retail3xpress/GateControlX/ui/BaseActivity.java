package com.Retail3xpress.GateControlX.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Retail3xpress.GateControlX.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import app.GateMasterApplication;
import busevent.BusEventDefault;
import busevent.CheckOutBusEvent;
import busevent.LoginBusEvent;
import busevent.NotificationBusEvent;
import busevent.PanicBusEvent;
import busevent.VisiteeDetails;
import datamodel.LoginDataModel;
import datamodel.PostCheckIn;
import datamodel.PostCheckOut;
import datamodel.PostDeviceToken;
import datamodel.ResponseCheckIn;
import datamodel.ResponseCheckOut;
import datamodel.ResponseDeviceToken;
import datamodel.ResponseVisiteeDetails;
import datamodel.TokenRefreshModel;
import datamodel.UserDetailRequest;
import db.DatabaseConnection;
import model.ActiveEntriesResponse;
import model.GetGatesResponse;
import model.LogoutResponse;
import model.NotificationResponse;
import model.PanicResponse;
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

    private View progressLayout, toplay;
    private ImageView profileIcon, spnrarrw, locationicon;
    private ProgressWheel progresswheel;
    protected LayoutInflater inflator = null;
    private Spinner gateSpinner;

    public GateApi gateApi;

    public TextView homePageTitle;

    public LayoutInflater getInflator() {
        return inflator;
    }





    protected View inflateView(int layoutId, String title, boolean isToolbar) {
        ViewGroup v = (ViewGroup) findViewById(R.id.content);
        homePageTitle.setText(title);
        if (BaseActivity.this instanceof HomeActivity) {
            gateSpinner.setVisibility(View.VISIBLE);
            spnrarrw.setVisibility(View.VISIBLE);
            profileIcon.setVisibility(View.VISIBLE);
            locationicon.setVisibility(View.VISIBLE);
        } else {
            gateSpinner.setVisibility(View.GONE);
            spnrarrw.setVisibility(View.GONE);
            profileIcon.setVisibility(View.GONE);
            locationicon.setVisibility(View.GONE);
        }
        gateSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    spnrarrw.setVisibility(View.INVISIBLE);
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    spnrarrw.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });


        View view = getInflator().inflate(layoutId, v);
        if (isToolbar) {
            toplay.setVisibility(View.VISIBLE);
        } else {
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
        toplay = (View) findViewById(R.id.toplay);
        homePageTitle = (TextView) findViewById(R.id.homePageTitle);
        gateSpinner = (Spinner) findViewById(R.id.gatespnr);
        gateSpinner.setDropDownVerticalOffset(100);
        spnrarrw = (ImageView) findViewById(R.id.gatespnrarrw);
        locationicon = (ImageView) findViewById(R.id.gatespnricon);

        Map<String, String> locationGateIdMap = databaseConnection.getGateLocationMap();
        List<String> locations = new ArrayList<>(locationGateIdMap.keySet());

// Create and set the adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spnr, locations);
        adapter.setDropDownViewResource(R.layout.item_spnr);
        gateSpinner.setAdapter(adapter);

        gateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = locations.get(position);
                String selectedGateId = locationGateIdMap.get(selectedLocation);

                Log.d(TAG, "Selected Location: " + selectedLocation);
                Log.d(TAG, "Corresponding Gate ID: " + selectedGateId);
                sharedPref.save("GATEID", selectedGateId);

                // Use selectedGateId as needed
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected (if needed)
            }
        });

        baseUrl = sharedPref.getString("baseUrl");
        if (baseUrl.length() > 0) {
            Constant.BASE_URL = baseUrl;
        }
        if (Constant.BASE_URL.length() > 0) {
            gateApi = ServiceGenerator.createService(GateApi.class, "", "");
        }
        profileIcon = (ImageView) findViewById(R.id.profileIcon);
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

        String devid = Util.getMacAddress(this);

        UserDetailRequest userDetailRequest = new UserDetailRequest();
        userDetailRequest.setEmployeeId(userID);
        userDetailRequest.setEmployeePin(password);
        userDetailRequest.setDeviceId(devid);
        userDetailRequest.setDeviceType(Constant.DEF_DEV);

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
                    sharedPref.saveBool("isloggedin", true);
                    sharedPref.save("lastlogin", Util.getcurrenttime());
                    sharedPref.save("pwd", password);
                    Constant.Exp_Time = response.body().getResponseData().getExpiresIn() / 3600;
                    int expiresIn = response.body().getResponseData().getExpiresIn(); // in seconds
                    scheduleTokenRefresh(expiresIn - 120); // Refresh 2 minutes before expiration
                    EventBus.getDefault().post(new LoginBusEvent("YES", active, response.body().getTitle()));
                } else {
                    active = 0;
                    try {
                        EventBus.getDefault().post(new LoginBusEvent("YES", active, getErrorFromResponse(response.errorBody().string())));
                    } catch (IOException e) {
                        EventBus.getDefault().post(new LoginBusEvent("YES", active,  e.getMessage().toString()));
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginDataModel> call, Throwable t) {
                isSuccess = false;
                active = 0;
                EventBus.getDefault().post(new LoginBusEvent("NO", active, t.getMessage()));
            }
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

    public long gettokentimediff() {
        String lastlogintime = sharedPref.getString("lastlogin");
        String currenttime = Util.getcurrenttime();

        long diff = Util.getDifferenceInHours(lastlogintime, currenttime, Constant.Date_Format);

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
        postCheckIn.setGuardId(sharedPref.getString("userid"));

        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<ResponseCheckIn> call = gateApi.postcheckin(token, postCheckIn);

        call.enqueue(new Callback<ResponseCheckIn>() {
            @Override
            public void onResponse(Call<ResponseCheckIn> call, Response<ResponseCheckIn> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTitle().equalsIgnoreCase("Created")) {
                        EventBus.getDefault().post(new BusEventDefault("PostCheckin", true,response.body().getMessage()));
                    } else {
                        EventBus.getDefault().post(new BusEventDefault("PostCheckin", false,response.body().getMessage()));
                    }
                } else {
                    try {
                        EventBus.getDefault().post(new BusEventDefault("PostCheckin",  false,getErrorFromResponse(response.errorBody().string())));
                    } catch (IOException e) {
                        EventBus.getDefault().post(new BusEventDefault("PostCheckin", false,e.getMessage().toString()));
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckIn> call, Throwable t) {
                EventBus.getDefault().post(new BusEventDefault("PostCheckin", false, t.getMessage()));
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
                    EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Success, pos, response.body().getMessage()));
                } else {
                    try {
                        EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Failed, pos, getErrorFromResponse(response.errorBody().string())));
                    } catch (IOException e) {
                        EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Failed, pos, e.getMessage()));
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckOut> call, Throwable t) {
                EventBus.getDefault().post(new CheckOutBusEvent(gatereqid, Constant.CheckOut_Failed, pos, t.getMessage().toString()));
            }
        });
    }


    public void getallgates() {
        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<GetGatesResponse> call = gateApi.getAllGates(token);

        call.enqueue(new Callback<GetGatesResponse>() {
            @Override
            public void onResponse(Call<GetGatesResponse> call, Response<GetGatesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GetGatesResponse data = response.body();

                    // Check the statusCode from the response
                    if ("200".equals(data.getStatusCode())) {
                       databaseConnection.deleteTable("Gates");
                    databaseConnection.insertGate(data);
                        Log.d("getallgates==========", data.toString());
                        hideProgress();
                        EventBus.getDefault().post(new BusEventDefault("All Gates", true));

                    } else {
                        // Handle unexpected status code (e.g., not 200)
                        EventBus.getDefault().post(new BusEventDefault("All Gates", false));
                        Log.e("getallgates", "Unexpected status code: " + data.getStatusCode());
                    }
                } else {
                    // Handle error case when response is not successful
                    try {
                        EventBus.getDefault().post(new BusEventDefault("All Gates", false,getErrorFromResponse(response.errorBody().string())));
                    } catch (IOException e) {
                        EventBus.getDefault().post(new BusEventDefault("All Gates", false,e.getMessage().toString()));
                        throw new RuntimeException(e);
                    }
                    Log.e("getallgates", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<GetGatesResponse> call, Throwable t) {
                // Handle failure
                Log.e("getAllgates", "Request failed: " + t.getMessage());
                EventBus.getDefault().post(new BusEventDefault("All Gates", false));
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
                    try {
                        EventBus.getDefault().post(new VisiteeDetails("", getErrorFromResponse(response.errorBody().string()), false));
                    } catch (IOException e) {
                        EventBus.getDefault().post(new VisiteeDetails("", e.getMessage().toString(), false));
                        throw new RuntimeException(e);
                    }
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
                        try {
                            EventBus.getDefault().post(new BusEventDefault("ActiveEntries", false,getErrorFromResponse(response.errorBody().string())));
                        } catch (IOException e) {
                            EventBus.getDefault().post(new BusEventDefault("ActiveEntries", false,getErrorFromResponse(e.getMessage().toString())));
                            throw new RuntimeException(e);
                        }
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

    public void updatefcmtoken(){
        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        PostDeviceToken postDeviceToken = new PostDeviceToken();
        postDeviceToken.setDeviceToken(sharedPref.getString("fcmtoken"));

        Call<ResponseDeviceToken> call = gateApi.postdevicetoken(token, postDeviceToken);

        call.enqueue(new Callback<ResponseDeviceToken>() {
            @Override
            public void onResponse(Call<ResponseDeviceToken> call, Response<ResponseDeviceToken> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: fcm token api --->"+response.body().getMessage());
                }else{
                    try {
                        Log.d(TAG, "onResponse: fcm token api --->"+response.errorBody().string());
                    } catch (IOException e) {
                        Log.d(TAG, "onResponse: fcm token api --->"+e.getMessage().toString());
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDeviceToken> call, Throwable t) {
                Log.d(TAG, "onResponse: fcm token api --->"+t.getMessage().toString());
            }
        });

    }


    public void logoutUser() {
        String token = "Bearer " + sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        Call<LogoutResponse> call = gateApi.logout(token);

        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LogoutResponse logoutResponse = response.body();
                    Log.d("Logout", "Logout message: " + logoutResponse.getMessage());
                    // Handle successful logout
                    dologout();
                } else {
                    try {
                        Util.showOKAlert(BaseActivity.this,getErrorFromResponse(response.errorBody().string()));
                    } catch (IOException e) {
                        Util.showOKAlert(BaseActivity.this,"LogOut Failed");
                        throw new RuntimeException(e);
                    }
                    // Handle logout failure
                    Log.d("Logout", "Logout failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                // Handle request failure
                Util.showOKAlert(BaseActivity.this,"LogOut Failed");
                Log.d("Logout", "Logout error: " + t.getMessage());
            }
        });
    }

        public void dologout(){
            stopTokenRefresh();
            databaseConnection.clearAllTables();
            finishAffinity();
            sharedPref.saveBool("isloggedin", false);
            Util.pushNext(BaseActivity.this, LoginActivity.class);
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


    private Handler handler;
    private Runnable tokenRefreshRunnable;

    // Call this method to schedule the token refresh
    private void scheduleTokenRefresh(int delayInSeconds) {
        if (handler == null) {
            handler = new Handler();  // Initialize Handler if it's not already initialized
        }

        // If tokenRefreshRunnable has already been initialized, we can reuse it.
        // Otherwise, create a new one.
        if (tokenRefreshRunnable == null) {
            tokenRefreshRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.d("TokenRefresh", "Run()");

                    if (Util.isNetworkAvailable(BaseActivity.this)) {
                        refreshToken();
                    }

                }
            };
        }

        handler.postDelayed(tokenRefreshRunnable, delayInSeconds * 1000L);
        Log.d("TokenRefresh", "Token refresh scheduled in " + delayInSeconds + " seconds.");
    }


    private void stopTokenRefresh() {
        if (handler == null || tokenRefreshRunnable == null) {
            Log.d("TokenRefresh", "Handler or Runnable is null");
        }
        if (handler != null && tokenRefreshRunnable != null) {
            handler.removeCallbacks(tokenRefreshRunnable);
            Log.d("TokenRefresh", "Token refresh timer stopped.");
        }
    }


    public void refreshToken() {
        String currentToken = sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        if (!currentToken.isEmpty()) {
            Call<TokenRefreshModel> call = gateApi.refreshToken("Bearer " + currentToken);

            call.enqueue(new Callback<TokenRefreshModel>() {
                @Override
                public void onResponse(Call<TokenRefreshModel> call, Response<TokenRefreshModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String newToken = response.body().getResponseData().getAccess_token();
                        int expiresIn = response.body().getResponseData().getExpires_in(); // in seconds
                        sharedPref.save(Constant.PREF_AUTH_TOKEN, newToken);
                        sharedPref.saveBool("isloggedin", true);
                        sharedPref.save("lastlogin", Util.getcurrenttime());
                        scheduleTokenRefresh(expiresIn - 120); // Refresh 2 minutes before expiration
                        EventBus.getDefault().post(new LoginBusEvent("YES", 1, "Token refresh successfull"));
                        Log.d("TokenRefresh", "Token refreshed successfully");
                    } else {
                        try {
                            EventBus.getDefault().post(new LoginBusEvent("NO", 0, getErrorFromResponse(response.errorBody().string())));
                        } catch (IOException e) {
                            EventBus.getDefault().post(new LoginBusEvent("NO", 0, e.getMessage().toString()));
                            throw new RuntimeException(e);
                        }
                        Log.e("TokenRefresh", "Token refresh failed: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<TokenRefreshModel> call, Throwable t) {
                    Log.e("TokenRefresh", "Token refresh failed", t);
                    EventBus.getDefault().post(new LoginBusEvent("NO", 0, t.getMessage()));
                }
            });
        }
    }


    public void panicAction() {
        showProgress();
        String currentToken = sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        if (!currentToken.isEmpty()) {
            Call<PanicResponse> call = gateApi.panicActionCall("Bearer " + currentToken);

            call.enqueue(new Callback<PanicResponse>() {
                @Override
                public void onResponse(Call<PanicResponse> call, Response<PanicResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        EventBus.getDefault().post(new PanicBusEvent("YES", response.body().getMessage()));
                        Log.d("panicAction", "panicAction successfully");
                    } else {
                        try {
                            EventBus.getDefault().post(new PanicBusEvent("NO", getErrorFromResponse(response.errorBody().string())));
                        } catch (IOException e) {
                            EventBus.getDefault().post(new PanicBusEvent("NO",  e.getMessage().toString()));
                            throw new RuntimeException(e);
                        }
                        Log.e("panicAction", "panicAction failed: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<PanicResponse> call, Throwable t) {
                    Log.e("panicAction", "panicAction failed", t);
                    EventBus.getDefault().post(new PanicBusEvent("NO",  t.getMessage()));
                }
            });
        }
    }


    public void getNotification() {
        String currentToken = sharedPref.getString(Constant.PREF_AUTH_TOKEN);
        if (!currentToken.isEmpty()) {
            Call<NotificationResponse> call = gateApi.getNotification("Bearer " + currentToken);

            call.enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        databaseConnection.deleteTable("Notifications");
                        databaseConnection.insertNotification(response.body());
                        Log.d("NotificationResponse==========", response.body().toString());
                        EventBus.getDefault().post(new NotificationBusEvent("YES", response.body().getTitle()));
                    } else {
                        try {
                            EventBus.getDefault().post(new NotificationBusEvent("NO", getErrorFromResponse(response.errorBody().string())));
                        } catch (IOException e) {
                            EventBus.getDefault().post(new NotificationBusEvent("NO", e.getMessage().toString()));
                            throw new RuntimeException(e);
                        }
                        Log.e("getNotification", "getNotification failed: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    Log.e("getNotification", "getNotification failed", t);
                    EventBus.getDefault().post(new NotificationBusEvent("NO",  t.getMessage()));
                }
            });
        }
    }


}
