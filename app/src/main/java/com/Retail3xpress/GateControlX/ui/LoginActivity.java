package com.Retail3xpress.GateControlX.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Retail3xpress.GateControlX.R;

import org.greenrobot.eventbus.Subscribe;

import busevent.BusEventDefault;
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
        inflateView(R.layout.login_portrait, "", false);
        userid = (EditText) findViewById(R.id.userid);
        password = (EditText) findViewById(R.id.password);
        progressLayout = (View) findViewById(R.id.progressLayout);
        progresswheel = (ProgressWheel) findViewById(R.id.progresswheel);
        loginBtn = (Button) findViewById(R.id.btn_signIn).findViewById(R.id.btn);
        fgtbtn = (Button) findViewById(R.id.btn_fgtpwd).findViewById(R.id.btn);

        loginBtn.setText("Login");
        fgtbtn.setText("Forgot Password");

//        userid.setText("001");
//        password.setText("001");

        fgtbtn.setVisibility(View.GONE);

        if (sharedPref.getBool("isloggedin")) {
            Util.pushNext(this, HomeActivity.class);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
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
                    userid.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = userid.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = userid.getRight() - userid.getPaddingEnd();
                                if (event.getX() >= drawableEnd - userid.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    userid.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    userid.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    password.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    password.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = password.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = password.getRight() - password.getPaddingEnd();
                                if (event.getX() >= drawableEnd - password.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    password.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    password.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
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

    public void onEvent(BusEventDefault event) {
        if (event.getMessage().equalsIgnoreCase("All Gates")) {
            if (event.getSuccess()) {
                hideProgress();
                Util.pushNext(this, HomeActivity.class);
            }
        }
    }

    @Subscribe
    public void onEvent(LoginBusEvent loginBusEvent) {
        hideProgress();
        if (loginBusEvent.getStrEvent() == "YES") {
            if (loginBusEvent.getActive() == 1 && loginBusEvent.getStatus().equalsIgnoreCase("OK")) {

                getallgates();
               // Util.pushNext(this, HomeActivity.class);

            } else {
                Util.showOKAlert(this,loginBusEvent.getStatus());
            }
        } else {
            Util.showOKAlert(this,loginBusEvent.getStatus());
        }
    }

    @Override

    public void onBackPressed() {
        finish();
        // Do nothing on  back button navigation
    }
}
