package app;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.Retail3xpress.GateControlX.ui.BaseActivity;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
import com.Retail3xpress.GateControlX.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import adapter.VisitorsAdapter;
import busevent.BusEventDefault;
import busevent.CheckOutBusEvent;
import db.DatabaseConnection;
import model.GateEntry;
import model.Visitor;
import utils.Constant;
import utils.Util;

public class SecondFragment extends Fragment {

    private RecyclerView recyclerView;
    private VisitorsAdapter visitorsAdapter;
    private List<Visitor> visitorsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private BaseActivity baseActivity;
    private EditText searchedt;
    private ImageButton searchbtn;
    private MaterialCardView scanbtn;
    private DatabaseConnection databaseConnection;

    public SecondFragment() {
        // Required empty public constructor
    }

    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEvent(CheckOutBusEvent checkOutBusEvent) {

        if (checkOutBusEvent.getType() == Constant.CheckOut_Call) {
            if (Util.isNetworkAvailable(this.requireActivity())) {
                swipeRefreshLayout.setRefreshing(true);
                baseActivity.postcheckoutapi(checkOutBusEvent.getReqID(), checkOutBusEvent.getPos());
            } else {
                Util.showOKAlert(this.getActivity(), "Please check your internet connection and try again later");
            }
        } else if (checkOutBusEvent.getType() == Constant.CheckOut_Success) {
            baseActivity.hideProgress();
            swipeRefreshLayout.setRefreshing(false);
            Util.showOKAlert(getActivity(), checkOutBusEvent.getMsg().isEmpty() ? "Check Out Successful" : checkOutBusEvent.getMsg());
            databaseConnection.deleteGateEntry(checkOutBusEvent.getReqID());
            int pos = visitorsList.indexOf(visitorsAdapter.getfilterlist().get(checkOutBusEvent.getPos()));
            visitorsList.remove(pos);
            visitorsAdapter.removeItem(checkOutBusEvent.getPos());
            visitorsAdapter.notifyItemRemoved(pos);
            visitorsAdapter.notifyDataSetChanged();
        } else if (checkOutBusEvent.getType() == Constant.CheckOut_Failed) {
            baseActivity.hideProgress();
            swipeRefreshLayout.setRefreshing(false);
            Util.showOKAlert(getActivity(), checkOutBusEvent.getMsg());
        }
    }

    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEvent(BusEventDefault event) {
        swipeRefreshLayout.setRefreshing(false);
        if (event.getEvent().equalsIgnoreCase("activeentries")) {
            if (event.getSuccess()) {
                setlist();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewVisitors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        scanbtn = view.findViewById(R.id.scancard);
        searchbtn = view.findViewById(R.id.searchbtn);
        searchedt = view.findViewById(R.id.searchedt);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.hideKeyBoard(getActivity());
                visitorsAdapter.filter(searchedt.getText().toString());
            }
        });

        searchedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    searchedt.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_delete, 0);
                    searchedt.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            Drawable drawable = searchedt.getCompoundDrawables()[2]; // Right drawable
                            if (drawable != null) {
                                int drawableEnd = searchedt.getRight() - searchedt.getPaddingEnd();
                                if (event.getX() >= drawableEnd - searchedt.getCompoundDrawables()[2].getBounds().width()) {
                                    // Drawable on the right clicked
                                    // Handle your action here
                                    searchedt.setText("");
                                    visitorsAdapter.filter("");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    searchedt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
            }
        });

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                                Barcode.FORMAT_PDF417)
                        .build();
                GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(getContext(), options);
                scanner.startScan().addOnSuccessListener(new OnSuccessListener<Barcode>() {
                    @Override
                    public void onSuccess(Barcode barcode) {
                        String data = barcode.getRawValue();
                        if (data.contains("%")) {
                            String[] scndata = data.split("%");
                            String carRegistration = scndata[6];
                            searchedt.setText(carRegistration);
                        }
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Util.showToast(getContext(), "Scan Cancelled");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Util.showOKAlert(getContext(), "Scan Failed with error - " + e.getMessage());
                    }
                });

            }
        });

        // Prepare your list of visitors (replace with actual data)
        databaseConnection = new DatabaseConnection(getContext());
        baseActivity = (BaseActivity) this.getActivity();
        assert baseActivity != null;

        swipeRefreshLayout.setRefreshing(true);
        baseActivity.getActiveEntries();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getActivity() != null) {
                    if (Util.isNetworkAvailable(getActivity())) {
                        baseActivity.showProgress();
                        baseActivity.getActiveEntries();
                    } else {
                        Util.showOKAlert(getActivity(), "Please check your internet connection and try again later");
                    }
                }

            }
        });

        return view;
    }

    public void setlist() {
        // Load data from the database
        visitorsList = loadVisitorsFromDatabase();

        // Set up the RecyclerView Adapter
        visitorsAdapter = new VisitorsAdapter(visitorsList,
                getActivity());
        recyclerView.setAdapter(visitorsAdapter);

    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private List<Visitor> loadVisitorsFromDatabase() {
        List<Visitor> visitors = new ArrayList<>();
        // Query the database
        List<GateEntry> entries = databaseConnection.getGateEntries();

        // Convert database entries to Visitor objects
        for (GateEntry entry : entries) {
            visitors.add(new Visitor(
                    entry.getVisitorName(),
                    entry.getVisitorMobile(),
                    entry.getVehicleRegistrationNumber(),
                    entry.getVisitorAddress(),
                    entry.getPurpose(),
                    entry.getEntryTime(),
                    entry.getCreatedType(),
                    entry.getModifiedType(),
                    entry.getCreatedAt(),
                    entry.getUpdatedAt(),
                    entry.getGateReqID()
            ));
        }

        return visitors;
    }

}
