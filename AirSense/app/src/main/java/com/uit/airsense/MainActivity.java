package com.uit.airsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.uit.airsense.Fragment.FragmentSignIn;
import com.uit.airsense.Fragment.FragmentSignUp;
import com.uit.airsense.Model.GlobalVars;
import com.uit.airsense.Model.LocaleHelper;

public class MainActivity extends AppCompatActivity {

    AppCompatButton btnLanguage;
    TextView textSwitcher;
    public Fragment fmSignUp, fmSignIn;

    private boolean isEnglish = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitVars();
        replaceFragment(fmSignIn);
        btnLanguage = findViewById(R.id.btnLanguage);
        isEnglish = LocaleHelper.getLanguage(this).equals("en");

        btnLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        });
    }
    private void changeLanguage() {
        String selectedLanguage;
        final String[] languages = {"Vietnamese", "English"};

        if (isEnglish) {
            selectedLanguage = "vi";
            GlobalVars.day = "vi";
            btnLanguage.setBackgroundResource(R.drawable.vietnamflat);
        } else {
            selectedLanguage = "en";
            GlobalVars.day = "en";
            btnLanguage.setBackgroundResource(R.drawable.vietnamflat);
        }

        isEnglish = !isEnglish; // Update the flag

        LocaleHelper.setLocale(this, selectedLanguage);

        // Instead of calling recreate(), consider restarting the activity with an Intent.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Call this method when you want to toggle between English and Vietnamese

    private void InitVars()
    {
        fmSignIn = new FragmentSignIn(this);
        fmSignUp = new FragmentSignUp(this);
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