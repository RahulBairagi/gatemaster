package com.example.gatemaster.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.Subscribe;

import db.DatabaseConnection;
import utils.Constant;
import utils.ProgressWheel;
import utils.Util;

public class ProfileActivity extends BaseActivity {
    private View progressLayout;
    private ProgressWheel progresswheel;

    private Button Logout;
    private TextView userid,
            useremail,
            userph,
            username;


    @Override
    protected void create(Bundle bundle) {
        databaseConnection = new DatabaseConnection(this);
        initData();

    }

    private void initData() {
        inflateView(R.layout.profile_view,"",false);
        progressLayout = (View) findViewById(R.id.progressLayout);
        progresswheel = (ProgressWheel) findViewById(R.id.progresswheel);
        Logout = (Button) findViewById(R.id.btn_logout).findViewById(R.id.btn);
        Logout.setText("Logout");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showAlert(ProfileActivity.this, "Are you sure want to logout the application", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            showProgress();
                            logoutUser();
                            //finishAffinity();
                        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
        userid = (TextView) findViewById(R.id.employeeId);
        useremail = (TextView) findViewById(R.id.email);
        userph = (TextView) findViewById(R.id.mobile);
        username = (TextView) findViewById(R.id.name);

        userid.setText("Employee ID: "+sharedPref.getString("userid"));
        useremail.setText("Email: "+sharedPref.getString("useremail"));
        userph.setText("Mobile: "+sharedPref.getString("userph"));
        username.setText(sharedPref.getString("username"));


    }


    @Subscribe

    @Override
    public void onBackPressed() {
        finish();
        // Do nothing on  back button navigation
    }
}
