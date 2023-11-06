package com.uit.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public Fragment sign_up, sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitVars();
        replaceFragment(sign_in);
    }
    private void InitVars()
    {
        sign_up = new SignUpFragment(this);
        sign_in = new SignInFragment(this);
    }
    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        if (fragment == sign_up)
        {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else
        {
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        ft.replace(R.id.fl_login, fragment);
        ft.commit();
    }
}