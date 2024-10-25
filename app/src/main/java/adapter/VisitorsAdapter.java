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

import com.example.gatemaster.ui.BaseActivity;
import com.mobile.gatemaster.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import busevent.CheckOutBusEvent;
import model.Visitor;
import utils.Constant;
import utils.Util;

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.VisitorViewHolder> {

    private List<Visitor> visitorsList;
    private Activity context;

    public VisitorsAdapter(List<Visitor> visitorsList, Activity context) {
        this.visitorsList = visitorsList;
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
        Visitor entry = visitorsList.get(position);
        holder.tvVisitorName.setText(entry.getVisitorName());
        holder.tvVisitorMobile.setText("Mobile: " + entry.getVisitorMobile());
        holder.tvVehicleRegistration.setText("Vehicle: " + entry.getVehicleRegistrationNumber());
        holder.tvVisitorAddress.setText("Address: " + entry.getVisitorAddress());
        holder.tvPurpose.setText("Purpose: " + entry.getPurpose());
        holder.tvEntryTime.setText("Entry Time: " + entry.getEntryTime());
        holder.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new CheckOutBusEvent(visitorsList.get(holder.getAdapterPosition()).getGateReqID(), Constant.CheckOut_Call,holder.getAdapterPosition(),""));
            }
        });
    }

    public void removeItem(int position) {
        visitorsList.remove(position);
        notifyItemRemoved(position); // Notify RecyclerView about item removal
        notifyItemRangeChanged(position, visitorsList.size()); // Refresh remaining items
    }

    @Override
    public int getItemCount() {
        return visitorsList.size();
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
            checkout = itemView.findViewById(R.id.checkoutbtn).findViewById(R.id.btn);
            checkout.setText("Check Out");
            context = itemView.getContext();
        }
    }
}
