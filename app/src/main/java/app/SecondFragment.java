package app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gatemaster.ui.BaseActivity;
import com.mobile.gatemaster.R;

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
            visitorsAdapter.notifyDataSetChanged();
            visitorsList.remove(checkOutBusEvent.getPos());
        } else if (checkOutBusEvent.getType() == Constant.CheckOut_Failed) {
            baseActivity.hideProgress();
            swipeRefreshLayout.setRefreshing(false);
            Util.showOKAlert(getActivity(), checkOutBusEvent.getMsg());
        }
    }

    @Subscribe(threadMode = org.greenrobot.eventbus.ThreadMode.MAIN)
    public void onEvent(BusEventDefault event) {
        swipeRefreshLayout.setRefreshing(false);
        if (event.getMessage().equalsIgnoreCase("activeentries")) {
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

        // Prepare your list of visitors (replace with actual data)
        databaseConnection = new DatabaseConnection(getContext());
        baseActivity = (BaseActivity) this.getActivity();
        assert baseActivity != null;

        swipeRefreshLayout.setRefreshing(true);
        baseActivity.getActiveEntries();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getActivity() != null){
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
