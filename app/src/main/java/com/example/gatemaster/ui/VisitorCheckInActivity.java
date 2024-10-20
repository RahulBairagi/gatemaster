package com.example.gatemaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.card.MaterialCardView;
import com.mobile.gatemaster.R;

public class VisitorCheckInActivity extends BaseActivity {
    Button scanVehDisc,checkin, visitorbtn;
    MaterialCardView visitorcard, deliverycard;
    Boolean visitortoggle= false, deliverytoggle = false;
    RelativeLayout mainrl, poprl;
    EditText visitormobinp, visitorphtxtinp, visitorNameEditText, addressEditText, carRegistrationEditText;

    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.visitor_chechin);
        init();
    }

    private void init(){

        scanVehDisc = (Button) findViewById(R.id.scanVehicleDiscButton).findViewById(R.id.btn);
        checkin = (Button) findViewById(R.id.checkinbtn).findViewById(R.id.btn);
        visitorbtn = (Button) findViewById(R.id.visitorphnbtn).findViewById(R.id.btn);
        visitorcard = findViewById(R.id.visitorcard);
        deliverycard = findViewById(R.id.deliverycard);
        mainrl = findViewById(R.id.mainrl);
        poprl = findViewById(R.id.poprl);
        visitormobinp = findViewById(R.id.visitormobinp);
        visitorphtxtinp = findViewById(R.id.visitorphtxtinp);

        scanVehDisc.setText("Scan Vehicle Disc");
        checkin.setText("Check In");
        visitorbtn.setText("Submit");

        mainrl.setVisibility(View.INVISIBLE);
        poprl.setVisibility(View.VISIBLE);

        visitorcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visitortoggle = !visitortoggle;
                visitorcard.setChecked(visitortoggle);
                if (visitortoggle){
                    visitorcard.setBackgroundColor(getColor(R.color.lightgrey));
                    deliverytoggle = false;
                    deliverycard.setBackgroundColor(getColor(R.color.white));
                }else{
                    visitorcard.setBackgroundColor(getColor(R.color.white));
                }
            }
        });

        visitorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visitormobinp.getText().length() == 10){
                    mainrl.setVisibility(View.VISIBLE);
                    poprl.setVisibility(View.INVISIBLE);
                    visitorphtxtinp.setText(visitormobinp.getText());
                }else{
                    visitormobinp.setError("Invalid Mobile Number");
                }
            }
        });


        deliverycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverytoggle = !deliverytoggle;
                deliverycard.setChecked(deliverytoggle);
                if (deliverytoggle){
                    deliverycard.setBackgroundColor(getColor(R.color.lightgrey));
                    visitortoggle = false;
                    visitorcard.setBackgroundColor(getColor(R.color.white));
                }else{
                    deliverycard.setBackgroundColor(getColor(R.color.white));
                }
            }
        });
    }
}
