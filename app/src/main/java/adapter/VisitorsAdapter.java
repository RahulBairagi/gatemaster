package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.gatemaster.R;

import java.util.List;

import model.Visitor;

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.VisitorViewHolder> {

    private List<Visitor> visitorsList;

    public VisitorsAdapter(List<Visitor> visitorsList) {
        this.visitorsList = visitorsList;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visitor, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Visitor entry = visitorsList.get(position);
        holder.tvVisitorName.setText(entry.getVisitorName());
        holder.tvVisitorMobile.setText("Mobile: " + entry.getVisitorMobile());
        holder.tvVehicleRegistration.setText("Vehicle: " + entry.getVehicleRegistrationNumber());
        holder.tvVisitorAddress.setText("Address: " + entry.getVisitorAddress());
        holder.tvPurpose.setText("Purpose: " + entry.getPurpose());
        holder.tvEntryTime.setText("Entry Time: " + entry.getEntryTime());
    }

    @Override
    public int getItemCount() {
        return visitorsList.size();
    }

    public static class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvVisitorName, tvVisitorMobile, tvVehicleRegistration, tvVisitorAddress, tvPurpose, tvEntryTime;
        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVisitorName = itemView.findViewById(R.id.tvVisitorName);
            tvVisitorMobile = itemView.findViewById(R.id.tvVisitorMobile);
            tvVehicleRegistration = itemView.findViewById(R.id.tvVehicleRegistration);
            tvVisitorAddress = itemView.findViewById(R.id.tvVisitorAddress);
            tvPurpose = itemView.findViewById(R.id.tvPurpose);
            tvEntryTime = itemView.findViewById(R.id.tvEntryTime);
        }
    }
}
