package com.example.gatemaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.gatemaster.R;

import utils.Util;

public class ForgotPasswordActivity extends BaseActivity {

    Button sendotpbtn, chngepwdbtn, verifyotpbtn;
    EditText otptxtinp, newpwdtxtinp, cnfpwdtxtinp;
    TextView phnnumtxt, frgtpwddesctxt;
    LinearLayout pwdlayout;


    @Override
    protected void create(Bundle bundle) {
        initdata();
    }

    private void initdata() {
        inflateView(R.layout.forgot_pwd);
        sendotpbtn = (Button) findViewById(R.id.sendotpbtn).findViewById(R.id.btn);
        chngepwdbtn = (Button) findViewById(R.id.chngepwdbtn).findViewById(R.id.btn);
        verifyotpbtn = (Button) findViewById(R.id.verifyotpbtn).findViewById(R.id.btn);
        phnnumtxt = (TextView) findViewById(R.id.phnnumtxt);
        frgtpwddesctxt = (TextView) findViewById(R.id.frgtpwddesctxt);
        otptxtinp = (EditText) findViewById(R.id.otptxtinp);
        newpwdtxtinp = (EditText) findViewById(R.id.newpwdtxtinp);
        cnfpwdtxtinp = (EditText) findViewById(R.id.cnfpwdtxtinp);
        pwdlayout = (LinearLayout) findViewById(R.id.pwdlayout);

        pwdlayout.setVisibility(View.INVISIBLE);
        otptxtinp.setVisibility(View.VISIBLE);
        sendotpbtn.setVisibility(View.VISIBLE);
        verifyotpbtn.setVisibility(View.VISIBLE);

        sendotpbtn.setText("Send OTP");
        chngepwdbtn.setText("Change Password");
        verifyotpbtn.setText("Verify OTP");
        phnnumtxt.setText("+91 912*******");

        verifyotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendotpbtn.setVisibility(View.INVISIBLE);
                verifyotpbtn.setVisibility(View.INVISIBLE);
                otptxtinp.setVisibility(View.INVISIBLE);
                phnnumtxt.setVisibility(View.INVISIBLE);
                pwdlayout.setVisibility(View.VISIBLE);

                frgtpwddesctxt.setText("Choose a Password which is not in a Pattern, Easy to remember and hard to guess for better securtiy");
            }
        });

        chngepwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.pushwithFinish(ForgotPasswordActivity.this, LoginActivity.class);
            }
        });
    }
}
