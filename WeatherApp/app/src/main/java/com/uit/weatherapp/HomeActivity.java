package com.uit.weatherapp;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.uit.weatherapp.API.APIManager;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    public Fragment dashboard, accountSetting, chart, fragment;
    public AnimatedBottomBar navBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                int selectedIndex = navBar.getSelectedIndex();
                if (selectedIndex == 0) {
                    finish();
                }
                navBar.selectTabAt(0, true);
            }
        });
//        APIManager.getData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitVars();
        InitViews();
        InitEvents();
        replaceFragmentLeftToRight(dashboard);
        navBar.selectTabAt(0, false);
    }
    private void InitVars()
    {
        dashboard = new DashboardFragment(this);
        chart = new ChartFragment(this);
        accountSetting = new AccountSettingFragment(this);
    }
    public void replaceFragmentRightToLeft(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ft.replace(R.id.fl_home, fragment);
        ft.commit();
    }
    public void replaceFragmentLeftToRight(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        ft.replace(R.id.fl_home, fragment);
        ft.commit();
    }
    private void InitViews() {
        navBar = findViewById(R.id.bottom_bar);
    }
    private void InitEvents() {
        navBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                if(lastIndex == 0 && newIndex == 1)
                    replaceFragmentRightToLeft(chart);
                else if(lastIndex == 1 && newIndex == 0)
                    replaceFragmentLeftToRight(dashboard);
                else if (lastIndex == 0 && newIndex == 2)
                    replaceFragmentRightToLeft(accountSetting);
                else if (lastIndex == 2 && newIndex == 0)
                    replaceFragmentLeftToRight(dashboard);
                else if (lastIndex == 1 && newIndex == 2)
                    replaceFragmentRightToLeft(accountSetting);
                else if(lastIndex == 2 && newIndex == 1)
                {
                    replaceFragmentLeftToRight(chart);
                    Log.d("Retrofit: ", GlobalVars.username + " / " + GlobalVars.password);
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
}