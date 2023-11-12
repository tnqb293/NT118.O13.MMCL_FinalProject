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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uit.weatherapp.model.LocaleHelper;

public class AccountSettingFragment extends Fragment {
    HomeActivity homeActivity;
    ImageView btnChange_Language;

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
                        String selectedLanguage = "vi"; // Mặc định là tiếng Việt

                        if (which == 1) {
                            selectedLanguage = "en"; // Nếu chọn English
                        }

                        // Thiết lập ngôn ngữ và làm mới activity
                        LocaleHelper.setLocale(homeActivity, selectedLanguage);
                        homeActivity.recreate();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}





