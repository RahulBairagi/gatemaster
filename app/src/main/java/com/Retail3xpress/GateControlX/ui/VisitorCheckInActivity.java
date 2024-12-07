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
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.Retail3xpress.GateControlX.R;

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

    private String name, address, carRegistration = "", licnum = "";

    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.visitor_chechin, "Check In", true);
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

        visitorNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    visitorNameEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    visitorNameEditText.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = visitorNameEditText.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = visitorNameEditText.getRight() - visitorNameEditText.getPaddingEnd();
                                if (event.getX() >= drawableEnd - visitorNameEditText.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    visitorNameEditText.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    visitorNameEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });
        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    addressEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    addressEditText.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = addressEditText.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = addressEditText.getRight() - addressEditText.getPaddingEnd();
                                if (event.getX() >= drawableEnd - addressEditText.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    addressEditText.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    addressEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });
        visitorphtxtinp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    visitorphtxtinp.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    visitorphtxtinp.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = visitorphtxtinp.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = visitorphtxtinp.getRight() - visitorphtxtinp.getPaddingEnd();
                                if (event.getX() >= drawableEnd - visitorphtxtinp.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    visitorphtxtinp.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    visitorphtxtinp.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });
        carRegistrationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    carRegistrationEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    carRegistrationEditText.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = carRegistrationEditText.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = carRegistrationEditText.getRight() - carRegistrationEditText.getPaddingEnd();
                                if (event.getX() >= drawableEnd - carRegistrationEditText.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    carRegistrationEditText.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    carRegistrationEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });
        purposeinptxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    purposeinptxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    purposeinptxt.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = purposeinptxt.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = purposeinptxt.getRight() - purposeinptxt.getPaddingEnd();
                                if (event.getX() >= drawableEnd - purposeinptxt.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    purposeinptxt.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    purposeinptxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });
        visitormobinp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    visitormobinp.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    visitormobinp.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = visitormobinp.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = visitormobinp.getRight() - visitormobinp.getPaddingEnd();
                                if (event.getX() >= drawableEnd - visitormobinp.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    visitormobinp.setText("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    visitormobinp.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });

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
                Util.hideKeyBoard(VisitorCheckInActivity.this);
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

        scanVehDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_PDF417)
                        .build();
                GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(VisitorCheckInActivity.this, options);
                scanner.startScan().addOnSuccessListener(new OnSuccessListener<Barcode>() {
                    @Override
                    public void onSuccess(Barcode barcode) {
                        parsescandata(barcode.getRawValue());
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Util.showToast(VisitorCheckInActivity.this, "Scan Cancelled");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Util.showOKAlert(VisitorCheckInActivity.this, "Scan Failed with error - " + e.getMessage());
                    }
                });

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

    public void parsescandata(String data) {
        if (data.contains("%")) {
            String[] scndata = data.split("%");
            carRegistration = scndata[7];
            licnum = scndata[6];
            carRegistrationEditText.setText(carRegistration);
            carRegistrationEditText.setEnabled(false);
        }
    }

}
