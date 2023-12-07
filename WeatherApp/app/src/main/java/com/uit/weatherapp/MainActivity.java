package com.uit.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    public Fragment sign_up, sign_in, reset_password_find_username, reset_password;

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
        reset_password_find_username = new ResetPasswordFindUsernameFragment(this);
        reset_password = new ResetPasswordFragment(this);
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
        else if(fragment == sign_in)
        {
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if (fragment == reset_password_find_username)
        {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else if (fragment == reset_password)
        {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        ft.replace(R.id.fl_login, fragment);
        ft.commit();
    }
}