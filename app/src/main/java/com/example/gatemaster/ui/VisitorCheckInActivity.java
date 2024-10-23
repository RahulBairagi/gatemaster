package com.example.gatemaster.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.card.MaterialCardView;
import com.mobile.gatemaster.R;

public class VisitorCheckInActivity extends BaseActivity {
    Button scanVehDisc, checkin, visitorbtn;
    MaterialCardView visitorcard, deliverycard;
    Boolean visitortoggle = false, deliverytoggle = false;
    RelativeLayout mainrl, poprl;
    EditText visitormobinp, visitorphtxtinp, visitorNameEditText, addressEditText, carRegistrationEditText;

    private String name, address , carRegistration = "";

    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.visitor_chechin);
        init();
    }

    @SuppressLint("Range")
    private void checkMobileNumber(String mobileNumber) {
        Cursor cursor = databaseConnection.getVisitorCursorByMobile(mobileNumber);

        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex("visitor_name"));
            address = cursor.getString(cursor.getColumnIndex("visitor_address"));
            carRegistration = cursor.getString(cursor.getColumnIndex("vehicle_registration_number"));
            cursor.close();
        } else {
            Log.d("Visitor Info", "No entry found for this mobile number.");
        }
    }

    private void init() {

        scanVehDisc = (Button) findViewById(R.id.scanVehicleDiscButton).findViewById(R.id.btn);
        checkin = (Button) findViewById(R.id.checkinbtn).findViewById(R.id.btn);
        visitorbtn = (Button) findViewById(R.id.visitorphnbtn).findViewById(R.id.btn);
        visitorcard = findViewById(R.id.visitorcard);
        deliverycard = findViewById(R.id.deliverycard);
        mainrl = findViewById(R.id.mainrl);
        poprl = findViewById(R.id.poprl);
        visitormobinp = findViewById(R.id.visitormobinp);
        visitorNameEditText = findViewById(R.id.visitorNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        visitorphtxtinp = findViewById(R.id.visitorphtxtinp);
        carRegistrationEditText = findViewById(R.id.carRegistrationEditText);

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
                if (visitortoggle) {
                    visitorcard.setBackgroundColor(getColor(R.color.lightgrey));
                    deliverytoggle = false;
                    deliverycard.setBackgroundColor(getColor(R.color.white));
                } else {
                    visitorcard.setBackgroundColor(getColor(R.color.white));
                }
            }
        });

        visitorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visitormobinp.getText().length() == 10) {
                    checkMobileNumber(visitormobinp.getText().toString());
                    mainrl.setVisibility(View.VISIBLE);
                    poprl.setVisibility(View.INVISIBLE);
                    visitorphtxtinp.setText(visitormobinp.getText().toString());
                    visitorNameEditText.setText(name);
                    addressEditText.setText(address);
                    carRegistrationEditText.setText(carRegistration);
                } else {
                    visitormobinp.setError("Invalid Mobile Number");
                }
            }
        });


        deliverycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deliverytoggle = !deliverytoggle;
                deliverycard.setChecked(deliverytoggle);
                if (deliverytoggle) {
                    deliverycard.setBackgroundColor(getColor(R.color.lightgrey));
                    visitortoggle = false;
                    visitorcard.setBackgroundColor(getColor(R.color.white));
                } else {
                    deliverycard.setBackgroundColor(getColor(R.color.white));
                }
            }
        });
    }
}
