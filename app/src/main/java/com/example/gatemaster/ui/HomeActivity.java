package com.example.gatemaster.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.AlertAdapter;
import adapter.CustomerDetailAdapter;
import busevent.InvoiceBusEvent;
import model.AlertItem;
import model.CustomerDetailModel;
import utils.ProgressWheel;
import utils.SpacesItemDecoration;
import utils.Util;

public class HomeActivity extends BaseActivity implements  CustomerDetailAdapter.ItemClickListener{

    private RecyclerView recyclerView;
    private AlertAdapter adapter;
    private ArrayList<AlertItem> alertItems;
    private View progressLayout;
    private ProgressWheel progresswheel;

    View checkInVisitorLayout ;
    View checkOutVisitorLayout ;
    View checkInPackageLayout ;
    View checkOutPackageLayout ;
    @Override
    protected void create(Bundle bundle) {
        inflateView(R.layout.home_portrait);
        init();
        initRecycleView();
    }

    private void init(){
         checkInVisitorLayout = findViewById(R.id.checkInVisitor);
         checkOutVisitorLayout = findViewById(R.id.checkOutVisitor);
        checkInPackageLayout = findViewById(R.id.checkInPackage);
        checkOutPackageLayout = findViewById(R.id.checkOutPackage);


        Button checkInButton = checkInVisitorLayout.findViewById(R.id.btn);
        checkInButton.setText("Check In");
        Button checkOutButton = checkOutVisitorLayout.findViewById(R.id.btn);
        checkOutButton.setText("Check Out");

        Button checkinButtonPk = checkInPackageLayout.findViewById(R.id.btn);
        checkinButtonPk.setText("Check In");

        Button checkOutButtonPk = checkOutPackageLayout.findViewById(R.id.btn);
        checkOutButtonPk.setText("Check Out");
    }


    private void initRecycleView() {
        try {
            recyclerView = findViewById(R.id.recyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
            recyclerView.setLayoutManager(layoutManager);

            alertItems = new ArrayList<>();

            alertItems.add(new AlertItem("Security Alert", "Suspicious activity detected"));
            alertItems.add(new AlertItem("Visitor Check-In", "John Doe checked in"));
            alertItems.add(new AlertItem("Package Received", "Package from Amazon delivered"));
            alertItems.add(new AlertItem("Fire Alarm", "Fire alarm triggered in Sector A"));
            alertItems.add(new AlertItem("System Update", "Security system update required"));

            AlertAdapter alertAdapter = new AlertAdapter(alertItems);
            recyclerView.setAdapter(alertAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.d("HomeActivity" + "position:::", position + "");


       //Util.pushNext(HomeActivity.this,SignActivity.class);
    }



}
