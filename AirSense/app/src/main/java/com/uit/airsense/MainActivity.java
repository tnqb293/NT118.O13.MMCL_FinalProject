package com.uit.airsense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.uit.airsense.Fragment.FragmentSignIn;
import com.uit.airsense.Fragment.FragmentSignUp;

public class MainActivity extends AppCompatActivity {
    TextView textSwitcher;
    public Fragment fmSignUp, fmSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitVars();
        replaceFragment(fmSignIn);

    }
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