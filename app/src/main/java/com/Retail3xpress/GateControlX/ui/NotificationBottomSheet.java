package com.Retail3xpress.GateControlX.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Retail3xpress.GateControlX.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import adapter.NotificationAdapter;
import db.DatabaseConnection;
import model.NotificationModel;


public class NotificationBottomSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Fetch data and set adapter
        List<NotificationModel> notifications = new DatabaseConnection(requireContext()).getNotifications();
        NotificationAdapter adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        // Set up RecyclerView, adapters, and other UI here
        view.findViewById(R.id.backIcon).setOnClickListener(v -> dismiss());
    }

    @Override
    public int getTheme() {
        // Use a full-screen BottomSheet theme
        return R.style.FullScreenBottomSheetDialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            view.setLayoutParams(layoutParams);
        }
    }


}
