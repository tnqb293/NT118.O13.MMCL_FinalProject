package com.uit.airsense;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.uit.airsense.API.APIManager;
import com.uit.airsense.Fragment.FragmentMap;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    public AnimatedBottomBar navBar;
    public Fragment fmMap;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitVars();
        InitViews();
        InitEvents();
        APIManager.getMap();
        replaceFragmentLeftToRight(fmMap);
        navBar.selectTabAt(0, false);

    }
    private void InitVars()
    {
        fmMap = new FragmentMap(this);
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

    }
}