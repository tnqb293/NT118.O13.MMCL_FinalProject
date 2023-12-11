package com.uit.airsense.Fragment;

import android.content.Intent;
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
import com.uit.airsense.MainActivity;
import com.uit.airsense.R;

public class FragmentAccount extends Fragment {
    HomeActivity homeActivity;
    AppCompatButton btInfoAcc, btLogout;
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
        btLogout = v.findViewById(R.id.btLogOut);
    }
    public void InitEvent()
    {
        btInfoAcc.setOnClickListener(v ->  {
            showDialogBarInfoAcc();
        });
        btLogout.setOnClickListener(v -> {
            startActivity(new Intent(homeActivity, MainActivity.class));
        });
    }
    public void showDialogBarInfoAcc()
    {
        DialogFragmentInfoAcc infoAccDialogFragment = DialogFragmentInfoAcc.newInstance();
        infoAccDialogFragment.show(getFragmentManager(), "rainfall_dialog");
    }
}
