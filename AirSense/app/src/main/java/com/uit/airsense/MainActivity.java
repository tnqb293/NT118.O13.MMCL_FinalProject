package com.uit.airsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uit.airsense.Fragment.FragmentFindUsername;
import com.uit.airsense.Fragment.FragmentSignIn;
import com.uit.airsense.Fragment.FragmentSignUp;
import com.uit.airsense.Model.LocaleHelper;

public class MainActivity extends AppCompatActivity {

    CardView btnLanguage;
    TextView textSwitcher;
    public Fragment fmSignUp, fmSignIn, fmFindUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitVars();
        replaceFragment(fmSignIn);
        btnLanguage = findViewById(R.id.btnLanguage);

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateLanguageButton();
    }

    private void changeLanguage() {
        String selectedLanguage;
        boolean isEnglish;
        isEnglish = LocaleHelper.getLanguage(this).equals("en");

        if (isEnglish) {
            selectedLanguage = "vi";
        } else {
            selectedLanguage = "en";
        }

        LocaleHelper.setLocale(this, selectedLanguage);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateLanguageButton() {
        String currentLanguage = LocaleHelper.getLanguage(this);

        if (currentLanguage.equals("en")) {
            btnLanguage.setBackgroundResource(R.drawable.englandflat);
        } else {
            btnLanguage.setBackgroundResource(R.drawable.vietnamflat);
        }
    }

    private void InitVars()
    {
        fmSignIn = new FragmentSignIn(this);
        fmSignUp = new FragmentSignUp(this);
        fmFindUser = new FragmentFindUsername(this);
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fragment == fmSignIn) {
            setupTextSwitcher(getString(R.string.log_in));
        } else if (fragment == fmSignUp) {
            setupTextSwitcher(getString(R.string.sign_up));
        }
        ft.replace(R.id.flScreenLogin, fragment);
        ft.commit();
    }

    private void setupTextSwitcher(String text) {
        textSwitcher = findViewById(R.id.tvSignIn);
        textSwitcher.setText(text);
    }
}