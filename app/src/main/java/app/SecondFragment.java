package app;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mobile.gatemaster.R;
import java.util.ArrayList;
import java.util.List;

import adapter.VisitorsAdapter;
import db.DatabaseConnection;
import model.GateEntry;
import model.Visitor;

public class SecondFragment extends Fragment {

    private RecyclerView recyclerView;
    private VisitorsAdapter visitorsAdapter;
    private List<Visitor> visitorsList;

    private DatabaseConnection databaseConnection;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewVisitors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Prepare your list of visitors (replace with actual data)
        databaseConnection = new DatabaseConnection(getContext());

        // Load data from the database
        visitorsList = loadVisitorsFromDatabase();

        // Set up the RecyclerView Adapter
        visitorsAdapter = new VisitorsAdapter(visitorsList,
                getActivity());
        recyclerView.setAdapter(visitorsAdapter);


        return view;
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
