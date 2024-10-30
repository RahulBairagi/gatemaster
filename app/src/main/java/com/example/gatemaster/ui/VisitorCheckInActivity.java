package com.example.gatemaster.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.card.MaterialCardView;
import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.Subscribe;

import busevent.BusEventDefault;
import busevent.VisiteeDetails;
import utils.Util;

public class VisitorCheckInActivity extends BaseActivity {
    Button scanVehDisc, checkinbtn, visitorphbtn;
    MaterialCardView visitorcard, deliverycard;
    Boolean visitortoggle = false, deliverytoggle = false;
    RelativeLayout mainrl, poprl;
    EditText visitormobinp, visitorphtxtinp, visitorNameEditText, addressEditText, carRegistrationEditText, purposeinptxt;

    private String name, address, carRegistration = "";

    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.visitor_chechin,"Check In",true);
        init();
    }

    @Subscribe
    public void onEvent(VisiteeDetails event) {
        hideProgress();
        if (event.getSuccess()) {
            mainrl.setVisibility(View.VISIBLE);
            poprl.setVisibility(View.INVISIBLE);
            name = event.getName();
            address = event.getAddress();
            visitorphtxtinp.setText(visitormobinp.getText().toString());
            visitorNameEditText.setText(name);
            addressEditText.setText(address);
        } else {
            mainrl.setVisibility(View.VISIBLE);
            poprl.setVisibility(View.INVISIBLE);
            visitorphtxtinp.setText(visitormobinp.getText().toString());
            visitorNameEditText.setText("");
            addressEditText.setText("");
            carRegistrationEditText.setText("");
        }
    }

    @Subscribe
    public void onEvent(BusEventDefault event) {
        hideProgress();

        if (event.getSuccess()) {
            Util.pushwithFinish(this, HomeActivity.class);
            Util.showToast(this, event.getMessage());
        } else {
            Util.showOKAlert(this, event.getMessage());
        }
    }

    private void init() {

        scanVehDisc = (Button) findViewById(R.id.scanVehicleDiscButton).findViewById(R.id.btn);
        checkinbtn = (Button) findViewById(R.id.checkinbtn).findViewById(R.id.btn);
        visitorphbtn = (Button) findViewById(R.id.visitorphnbtn).findViewById(R.id.btn);
        visitorcard = findViewById(R.id.visitorcard);
        deliverycard = findViewById(R.id.deliverycard);
        mainrl = findViewById(R.id.mainrl);
        poprl = findViewById(R.id.poprl);
        visitormobinp = findViewById(R.id.visitormobinp);
        visitorNameEditText = findViewById(R.id.visitorNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        visitorphtxtinp = findViewById(R.id.visitorphtxtinp);
        carRegistrationEditText = findViewById(R.id.carRegistrationEditText);
        purposeinptxt = findViewById(R.id.purposeinptxt);

        scanVehDisc.setText("Scan Vehicle Disc");
        checkinbtn.setText("Check In");
        visitorphbtn.setText("Submit");

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

        visitorphbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Util.isNetworkAvailable(VisitorCheckInActivity.this)) {
                    showProgress();
                    getvisitingdetails(visitormobinp.getText().toString());
                } else {
                    Util.showOKAlert(VisitorCheckInActivity.this, "Please check your internet connection and try again later");
                }
            }
        });

        checkinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkinvalidate()) {
                    if (Util.isNetworkAvailable(VisitorCheckInActivity.this)) {
                        showProgress();
                        // Call post check-in here
                        String visitttype = "";
                        if (visitortoggle) {
                            visitttype = "1";
                        } else if (deliverytoggle) {
                            visitttype = "2";
                        }
                        postCheckIn("1", visitorNameEditText.getText().toString(), addressEditText.getText().toString(), purposeinptxt.getText().toString(), carRegistrationEditText.getText().toString(), visitttype, visitorphtxtinp.getText().toString());
                    } else {
                        Util.showOKAlert(VisitorCheckInActivity.this, "Please check your internet connection and try again later");
                    }
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

    public boolean checkinvalidate() {
        boolean validate = true;

        if (visitorNameEditText.getText().length() == 0) {
            visitorNameEditText.setError("Invalid Name");
            validate = false;
        } else if (addressEditText.getText().length() == 0) {
            addressEditText.setError("Invalid Address");
            validate = false;
        } else if (carRegistrationEditText.getText().length() == 0) {
            carRegistrationEditText.setError("Invalid Car Registration");
            validate = false;
        } else if (purposeinptxt.getText().length() == 0) {
            purposeinptxt.setError("Invalid Purpose Details");
            validate = false;
        } else if (deliverytoggle == false && visitortoggle == false) {
            Util.showToast(VisitorCheckInActivity.this, "Select Visit Type");
            validate = false;
        }
        return validate;
    }

}
