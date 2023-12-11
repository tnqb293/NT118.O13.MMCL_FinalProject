package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.uit.airsense.API.APIManager;
import com.uit.airsense.Interface.DataUserCallback;
import com.uit.airsense.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DialogFragmentInfoAcc extends DialogFragment {
    TextView tvId, tvCreateOn, tvUsername, tvEmail;
    AppCompatButton btCancel;

    public static DialogFragmentInfoAcc newInstance() {
        return new DialogFragmentInfoAcc();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_info_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    public void InitEvent()
    {
        getInfo();
        btCancel.setOnClickListener(v -> {
            dismiss();
        });
    }
    public void InitViews(View v)
    {
        tvId = v.findViewById(R.id.tvValueUid);
        tvUsername = v.findViewById(R.id.tvValueUsername);
        tvCreateOn = v.findViewById(R.id.tvValueCreateOn);
        tvEmail = v.findViewById(R.id.tvValueEmail);
        btCancel = v.findViewById(R.id.btCancel);
    }
    public void getInfo() {
        APIManager.getInfoUser(new DataUserCallback() {
            @Override
            public void onSuccess(String id, String username, String email, long createOn) {
                tvId.setText(id);
                tvUsername.setText(username);
                tvEmail.setText(email);
                Date date = new Date(createOn);
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy, h:mma", Locale.ENGLISH);
                String dateFormat = sdf.format(date);
                tvCreateOn.setText(dateFormat);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
