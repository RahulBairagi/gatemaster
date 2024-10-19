package com.example.gatemaster.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.Subscribe;

import busevent.LoginBusEvent;
import db.DatabaseConnection;
import utils.ProgressWheel;
import utils.Util;

public class LoginActivity extends BaseActivity {
    private View progressLayout;
    private ProgressWheel progresswheel;

    private EditText userid, password, store;
    private Button loginBtn, fgtbtn;


    @Override
    protected void create(Bundle bundle) {
        databaseConnection = new DatabaseConnection(this);
        initData();

    }

    private void initData() {
        inflateView(R.layout.login_portrait);
        userid = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.password);
        progressLayout = (View) findViewById(R.id.progressLayout);
        progresswheel = (ProgressWheel) findViewById(R.id.progresswheel);
        loginBtn = (Button) findViewById(R.id.btn_signIn).findViewById(R.id.btn);
        fgtbtn = (Button) findViewById(R.id.btn_fgtpwd).findViewById(R.id.btn);

        loginBtn.setText("Login");
        fgtbtn.setText("Forgot Password");

//        userid.setText("EMP-001");
//        password.setText("123456");

        fgtbtn.setVisibility(View.GONE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    loginBtnCLick();
                }
            }
        });

        fgtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.pushNext(LoginActivity.this, ForgotPasswordActivity.class);
            }
        });

        userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    userid.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                } else {
                    userid.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });

    }

    private boolean validate() {
        boolean flag = true;
        if (userid.getText().toString().isEmpty()) {
            flag = false;
            Util.showToast(this, "Please enter the userid");
        } else if (password.getText().toString().isEmpty()) {
            flag = false;
            Util.showToast(this, "Please enter the password");
        }
        return flag;
    }

    private void loginBtnCLick() {
        Util.hideKeyBoard(LoginActivity.this);
        if (Util.isNetworkAvailable(this)) {
            if (validate()) {
                showProgress();
                executeLoginApi(userid.getText().toString(), password.getText().toString());
            }
        } else {
            Util.showOKAlert(this, "Please check your internet connection and try again later");
        }

    }

    @Subscribe
    public void onEvent(LoginBusEvent loginBusEvent) {
        hideProgress();

        if (loginBusEvent.getStrEvent() == "YES") {
            if (loginBusEvent.getActive() == 1 && loginBusEvent.getStatus().equalsIgnoreCase("OK")) {
                Util.pushNext(this, HomeActivity.class);
            } else {
                Toast.makeText(this, loginBusEvent.getStatus(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        // Do nothing on  back button navigation
    }
}
