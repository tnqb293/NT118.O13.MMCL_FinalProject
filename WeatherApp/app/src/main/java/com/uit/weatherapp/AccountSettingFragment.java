package com.uit.weatherapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uit.weatherapp.API.APIManager;
import com.uit.weatherapp.Interface.InforUserCallback;
import com.uit.weatherapp.model.LocaleHelper;
import com.uit.weatherapp.model.User;

public class AccountSettingFragment extends Fragment {
    HomeActivity homeActivity;
    ImageView btnChange_Language;
    TextView tvEmail;
    TextView tvUsername;
    TextView tvID;
    TextView tvDate;
    TextView tvService;

    Button btLogout;

    public AccountSettingFragment() {
    }

    public AccountSettingFragment(HomeActivity activity) {
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
        APIManager.getUser(new InforUserCallback() {
            @Override
            public void onSuccess(User user) {
                String statusService = "Disable";
                if (user.serviceAccount)
                    statusService = "Enable";
                tvEmail.setText(user.email);
                tvUsername.setText(user.username);
                tvID.setText(user.id);
                tvDate.setText(user.createdOn);

                tvService.setText(statusService);
            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });
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
        btnChange_Language = v.findViewById(R.id.bt_edit_language);
        tvEmail = v.findViewById(R.id.tv_full_email);
        tvUsername = v.findViewById(R.id.tv_full_username);
        tvID = v.findViewById(R.id.tv_full_id);
        tvDate = v.findViewById(R.id.tv_create_date);
        tvService = v.findViewById(R.id.tv_status_service);
    }

    public void InitEvent() {
        btLogout.setOnClickListener(v -> {
            startActivity(new Intent(homeActivity, MainActivity.class));
        });
        btnChange_Language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });
    }
    private void showLanguageDialog() {
        final String[] languages = {getString(R.string.vietnamese), getString(R.string.english)};
        AlertDialog.Builder builder = new AlertDialog.Builder(homeActivity);
        builder.setTitle(R.string.choose_a_language)
                .setItems(languages, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedLanguage = "vi";
                            GlobalVars.day = "vi";
                        if (which == 1) {
                            selectedLanguage = "en";
                            GlobalVars.day = "en";
                        }

                        LocaleHelper.setLocale(homeActivity, selectedLanguage);
                        homeActivity.recreate();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}





