package app;

import static androidx.core.content.res.ResourcesCompat.getColor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gatemaster.ui.BaseActivity;
import com.example.gatemaster.ui.HomeActivity;
import com.example.gatemaster.ui.VisitorCheckInActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.gatemaster.R;

import java.io.Serializable;
import java.util.ArrayList;

import adapter.AlertAdapter;
import db.DatabaseConnection;
import model.AlertItem;
import utils.Constant;
import utils.ProgressWheel;
import utils.SharedPref;
import utils.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private AlertAdapter adapter;
    private ArrayList<AlertItem> alertItems;
    private View progressLayout;
    private ProgressWheel progresswheel;

    View checkInVisitorLayout,view;
    BottomNavigationView bottomNavigationView;
    TextView guardinfolbl;
    private DatabaseConnection databaseConnection;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void init(View view) {
        databaseConnection = new DatabaseConnection(getContext());
        checkInVisitorLayout = view.findViewById(R.id.checkInVisitor);
        recyclerView = view.findViewById(R.id.recyclerView);
        guardinfolbl = view.findViewById(R.id.guardname_lbl);
        guardinfolbl.setText(Constant.GuardName);
        Button checkInButton = checkInVisitorLayout.findViewById(R.id.btn);
        Button panicbtn = view.findViewById(R.id.panicbtn);
        checkInButton.setText("Check In");
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.pushNext(getActivity(), VisitorCheckInActivity.class);
            }
        });

        panicbtn.setText("PANIC");

    }

    private void initRecycleView() {
        try {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

            alertItems = new ArrayList<>();

            alertItems.add(new AlertItem("Security Alert", "Suspicious activity detected"));
            alertItems.add(new AlertItem("Visitor Check-In", "John Doe checked in"));
//            alertItems.add(new AlertItem("Package Received", "Package from Amazon delivered"));
//            alertItems.add(new AlertItem("Fire Alarm", "Fire alarm triggered in Sector A"));
//            alertItems.add(new AlertItem("System Update", "Security system update required"));

            AlertAdapter alertAdapter = new AlertAdapter(alertItems);
            recyclerView.setAdapter(alertAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_first, container, false);
        init(view);
        initRecycleView();

        return view;
    }
}