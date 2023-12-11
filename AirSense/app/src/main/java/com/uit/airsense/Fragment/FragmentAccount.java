package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.uit.airsense.HomeActivity;
import com.uit.airsense.R;

public class FragmentAccount extends Fragment {
    HomeActivity homeActivity;
    DialogFragment fmInfoAcc;
    AppCompatButton btInfoAcc;
    public FragmentAccount(HomeActivity home)
    {
        this.homeActivity = home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    public void InitViews(View v)
    {
        btInfoAcc = v.findViewById(R.id.btInforAccount);
    }
    public void InitEvent()
    {
        btInfoAcc.setOnClickListener(v ->  {
            showDialogBarInfoAcc();
        });
    }
    public void showDialogBarInfoAcc()
    {
        DialogFragmentRainfall rainfallDialogFragment = DialogFragmentRainfall.newInstance();
        rainfallDialogFragment.show(getFragmentManager(), "rainfall_dialog");
    }
}
