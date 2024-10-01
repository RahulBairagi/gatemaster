package com.example.gatemaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.gatemaster.R;

public class VisitorCheckInActivity extends BaseActivity implements View.OnClickListener{
    Button scanDrivLic,scanVehDisc,checkOut;
    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.visitor_chechin);
        init();
    }


    @Override
    public void onClick(View view) {

    }

    private void init(){
        scanDrivLic = (Button) findViewById(R.id.scanDriverLicenseButton).findViewById(R.id.btn);
        scanVehDisc = (Button) findViewById(R.id.scanVehicleDiscButton).findViewById(R.id.btn);
        checkOut = (Button) findViewById(R.id.checkOutButton).findViewById(R.id.btn);

        scanDrivLic.setText("Scan Driver License");
        scanVehDisc.setText("Scan Vehicle Disc");
        checkOut.setText("Check Out ");
    }
}
