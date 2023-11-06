package com.uit.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountSettingFragment extends Fragment {
    HomeActivity homeActivity;
    Button btLogout;
    public AccountSettingFragment() {}
    public AccountSettingFragment(HomeActivity activity)
    {
        this.homeActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitViews(view);
        InitEvent();
//        tvValueHumidity.setText(APIClient.humidity);
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);

    }

    public void InitViews(View v) {
        btLogout = v.findViewById(R.id.bt_logout);
    }
    public void InitEvent() {
        btLogout.setOnClickListener(v -> {

        });
    }
}
