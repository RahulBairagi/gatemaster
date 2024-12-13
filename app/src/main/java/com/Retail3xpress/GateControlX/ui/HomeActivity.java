package com.Retail3xpress.GateControlX.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.Retail3xpress.GateControlX.R;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.Subscribe;

import app.FirstFragment;
import app.SecondFragment;
import app.ThirdFragment;
import busevent.LoginBusEvent;
import busevent.NotificationBusEvent;
import utils.Constant;
import utils.SharedPref;
import utils.Util;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HOMEACTIVITY";
    BottomNavigationView bottomNavigationView;
    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;

    @Subscribe
    public void onEvent(LoginBusEvent loginBusEvent) {
        hideProgress();
        if (loginBusEvent.getStrEvent() == "YES") {
            if (!(loginBusEvent.getActive() == 1 && loginBusEvent.getStatus().equalsIgnoreCase("OK"))) {
                Toast.makeText(this, loginBusEvent.getStatus(), Toast.LENGTH_SHORT).show();
                updatefcmtoken();
                getActiveEntries();
                getNotification();
            }
        }else{
            dologout();
        }
    }



    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.home_portrait,"",true);
        Constant.GuardName = sharedPref.getString("username");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission();
        } else {
            // For earlier versions, directly initialize FCM
            initializeFCM();
        }
        if (gettokentimediff() > Constant.Exp_Time){
            if (Util.isNetworkAvailable(this)) {
                showProgress();
                refreshToken();
            } else {
                Util.showOKAlert(this, "Please check your internet connection and try again later");
            }
        }else{
            updatefcmtoken();
            getActiveEntries();
            getNotification();
        }

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.tabhome);

    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
        } else {
            // Permission already granted
            initializeFCM();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                initializeFCM();
            } else {
                // Permission denied
                Log.e(TAG, "Notification permission denied.");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void initializeFCM() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    sharedPref.save("fcmtoken", token);
                    Log.d(TAG, "FCM Token: " + token);
                });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tabhome:
                inflateView(R.layout.home_portrait,"",true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, firstFragment)
                        .commit();
                return true;

            case R.id.tabvisitor:
                inflateView(R.layout.home_portrait,"",false);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, secondFragment)
                        .commit();
                return true;

            case R.id.tabnotification:
                inflateView(R.layout.home_portrait,"",false);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, thirdFragment)
                        .commit();
                return true;
        }
        return false;
    }
}

