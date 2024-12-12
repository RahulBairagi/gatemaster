package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.Retail3xpress.GateControlX.R;

import java.util.List;

import model.AlertItem;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertViewHolder> {

    private List<AlertItem> alertItemList;

    public AlertAdapter(List<AlertItem> alertItemList) {
        this.alertItemList = alertItemList;
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item (item_alert.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alert, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        // Bind the data to the view holder
        AlertItem alertItem = alertItemList.get(position);
        holder.headerTextView.setText(alertItem.getHeader());
        holder.subheaderTextView.setText(alertItem.getSubheader());
        // You can set icon or any other attributes here as needed
    }

    @Override
    public int getItemCount() {
        return alertItemList.size();
    }

    public static class AlertViewHolder extends RecyclerView.ViewHolder {

        TextView headerTextView;
        TextView subheaderTextView;
        ImageView iconImageView;
        ImageView alertImageView;

        public AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views in the item layout
            headerTextView = itemView.findViewById(R.id.headerTextView);
            subheaderTextView = itemView.findViewById(R.id.subheaderTextView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            alertImageView = itemView.findViewById(R.id.alertImageView);
        }
    }
}

