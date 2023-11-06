package com.uit.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChartFragment extends Fragment {
    HomeActivity homeActivity;
    ImageView ivChart;
    public ChartFragment() {}
    public ChartFragment(HomeActivity activity)
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
        return inflater.inflate(R.layout.fragment_chart, container, false);

    }

    public void InitViews(View v) {
        ivChart = v.findViewById(R.id.iv_chart);
    }
    public void InitEvent() {

    }
}
