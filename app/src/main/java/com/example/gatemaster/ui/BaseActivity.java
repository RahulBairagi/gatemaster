package com.example.gatemaster.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import javax.inject.Inject;

import app.GateMasterApplication;
import busevent.BusEventDefault;
import busevent.InvoiceBusEvent;
import busevent.InvoiceDetailBusEvent;
import busevent.LoginBusEvent;
import busevent.SignatureBusEvent;
import busevent.VersionBusEvent;
import datamodel.CustomerInvoiceDataModel;
import datamodel.CustomerInvoiceRequest;
import datamodel.InvoiceDataModel;
import datamodel.InvoiceDetailRequest;
import datamodel.LoginDataModel;
import datamodel.SignatureDataModel;
import datamodel.SignatureDetailRequest;
import datamodel.UserDetailRequest;
import datamodel.VersionDataModel;
import datamodel.VersionDetailRequest;
import db.DatabaseConnection;
import model.ActiveEntriesResponse;
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

    private View progressLayout;
    private ProgressWheel progresswheel;
    protected LayoutInflater inflator = null;

    public GateApi gateApi;

    public LayoutInflater getInflator() {
        return inflator;
    }

    protected View inflateView(int layoutId) {
        ViewGroup v = (ViewGroup) findViewById(R.id.content);
        View view = getInflator().inflate(layoutId, v);
        return view;
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
        baseUrl = sharedPref.getString("baseUrl");
        if (baseUrl.length() > 0) {
            Constant.BASE_URL = baseUrl;
        }
        if (Constant.BASE_URL.length() > 0) {
            gateApi = ServiceGenerator.createService(GateApi.class, "", "");
        }
        create(savedInstanceState);
    }

    protected abstract void create(Bundle bundle);

    @Override
    public void onBackPressed() {
        if (BaseActivity.this instanceof HomeActivity) {
            Util.showAlert(this, "Are you sure want to exit the application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE){
                        finishAffinity();
                    }
                    else if (which == DialogInterface.BUTTON_NEGATIVE){
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
                    }
                    else {
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
                    Constant.PREF_AUTH_TOKEN = response.body().getResponseData().getAccessToken().toString();
                    sharedPref.save(Constant.PREF_AUTH_TOKEN, response.body().getResponseData().getAccessToken().toString());
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
            };

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

    public void getActiveentries() {
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
                    } else {
                        // Handle unexpected status code (e.g., not 200)
                        Log.e("getActiveentries", "Unexpected status code: " + data.getStatusCode());
                    }
                } else {
                    // Handle error case when response is not successful
                    Log.e("getActiveentries", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ActiveEntriesResponse> call, Throwable t) {
                // Handle failure
                Log.e("getActiveentries", "Request failed: " + t.getMessage());
            }
        });
    }

}
