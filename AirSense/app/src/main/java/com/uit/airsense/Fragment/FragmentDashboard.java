package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uit.airsense.HomeActivity;
import com.uit.airsense.R;

public class FragmentDashboard extends Fragment {
    HomeActivity homeActivity;
    ImageView ivTemperature;
    public FragmentDashboard(HomeActivity activity)
    {
        this.homeActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ViewFlipper viewFlipper = view.findViewById(R.id.vfMainInfo1);

        if (viewFlipper != null) {
            viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
            viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);

            viewFlipper.setAutoStart(true);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.startFlipping();
        } else {
            Log.e("ViewFlipper", "ViewFlipper not found");
        }

        return view;
    }
    public void InitViews(View view)
    {
        ivTemperature = view.findViewById(R.id.ivTemperature);
    }
    public void InitEvent()
    {
        ivTemperature.setOnClickListener(v -> {
            showDialogBarTemperature();
        });
    }
    public void showDialogBarTemperature() {
        // Trong Fragment
        DialogFragmentTemperature temperatureDialogFragment = DialogFragmentTemperature.newInstance();
        temperatureDialogFragment.show(getFragmentManager(), "temperature_dialog");
    }

}
