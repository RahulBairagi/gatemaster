package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.Retail3xpress.GateControlX.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import busevent.CheckOutBusEvent;
import model.Visitor;
import utils.Constant;

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.VisitorViewHolder> {

    private List<Visitor> visitorList;
    private List<Visitor> filteredList;
    private Activity context;

    public VisitorsAdapter(List<Visitor> visitorsList, Activity context) {
        this.visitorList = new ArrayList<>(visitorsList);
        this.filteredList = new ArrayList<>(visitorsList);
        this.context = context;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visitor, parent, false);
        return new VisitorViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Visitor entry = filteredList.get(position);
        holder.tvVisitorName.setText(entry.getVisitorName());
        holder.tvVisitorMobile.setText("Mobile: " + entry.getVisitorMobile());
        holder.tvVehicleRegistration.setText("Vehicle: " + entry.getVehicleRegistrationNumber());
        holder.tvVisitorAddress.setText("Address: " + entry.getVisitorAddress());
        holder.tvPurpose.setText("Purpose: " + entry.getPurpose());
        holder.tvEntryTime.setText("Entry Time: " + entry.getEntryTime());
        holder.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CheckOutBusEvent(filteredList.get(holder.getAdapterPosition()).getGateReqID(), Constant.CheckOut_Call,holder.getAdapterPosition(),""));
            }
        });
    }

    public void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(visitorList); // Show all items when query is empty
        } else {
            for (Visitor item : visitorList) {
                if (item.getVehicleRegistrationNumber().toUpperCase().contains(query.toUpperCase()) || item.getVisitorName().toUpperCase().contains(query.toUpperCase())) {
                    filteredList.add(item); // Add matching items
                }
            }
        }
        notifyDataSetChanged(); // Notify the adapter about data changes
    }


    public void removeItem(int position) {
        int pos = visitorList.indexOf(filteredList.get(position));
        filteredList.remove(position);
        visitorList.remove(pos);
        notifyItemRemoved(position); // Notify RecyclerView about item removal
        notifyItemRangeChanged(position, filteredList.size()); // Refresh remaining items
    }

    public List<Visitor> getfilterlist(){
        return this.filteredList;
    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public static class VisitorViewHolder extends RecyclerView.ViewHolder {
        TextView tvVisitorName, tvVisitorMobile, tvVehicleRegistration, tvVisitorAddress, tvPurpose, tvEntryTime;
        Button checkout;
        Context context;

        public VisitorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVisitorName = itemView.findViewById(R.id.tvVisitorName);
            tvVisitorMobile = itemView.findViewById(R.id.tvVisitorMobile);
            tvVehicleRegistration = itemView.findViewById(R.id.tvVehicleRegistration);
            tvVisitorAddress = itemView.findViewById(R.id.tvVisitorAddress);
            tvPurpose = itemView.findViewById(R.id.tvPurpose);
            tvEntryTime = itemView.findViewById(R.id.tvEntryTime);
            checkout = itemView.findViewById(R.id.checkoutbtn);
            context = itemView.getContext();
        }
    }
}
