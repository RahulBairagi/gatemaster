package com.example.gatemaster.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.Subscribe;

import app.FirstFragment;
import app.SecondFragment;
import app.ThirdFragment;
import busevent.LoginBusEvent;
import utils.Constant;
import utils.SharedPref;
import utils.Util;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    BottomNavigationView bottomNavigationView;
    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();


    @Subscribe
    public void onEvent(LoginBusEvent loginBusEvent) {
        hideProgress();
        if (loginBusEvent.getStrEvent() == "YES") {
            if (!(loginBusEvent.getActive() == 1 && loginBusEvent.getStatus().equalsIgnoreCase("OK"))) {
                Toast.makeText(this, loginBusEvent.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Util.showToast(HomeActivity.this,loginBusEvent.getStatus());
        }
    }

    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.home_portrait,"",true);
        Constant.GuardName = sharedPref.getString("username");

        if (gettokentimediff() > Constant.Exp_Time){
            if (Util.isNetworkAvailable(this)) {
                showProgress();
                executeLoginApi(sharedPref.getString("userid"), sharedPref.getString("pwd"));
            } else {
                Util.showOKAlert(this, "Please check your internet connection and try again later");
            }
        }

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.tabhome);

        getActiveEntries();

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

