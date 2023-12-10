package com.uit.airsense.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.uit.airsense.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class DialogFragmentTemperature extends DialogFragment {
    DialogFragment hourFragment, dayFragment, weekFragment, monthFragment;
    AnimatedBottomBar navBar;
    public static DialogFragmentTemperature newInstance() {
        return new DialogFragmentTemperature();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_sheet, container, false);

        // Thêm Fragment vào Dialog
        hourFragment = new DialogFragmentHourTemperature();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fl_temperature, hourFragment) // R.id.fl_temperature là ID của layout trong dialog_sheet_temperature.xml để chứa Fragment
                .commit();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.bg_dialog_info);
        setCancelable(true);
        dayFragment = new DialogFragmentDayTemperature();
        weekFragment = new DialogFragmentWeekTemperature();
        monthFragment = new DialogFragmentMonthTemperature();
        InitViews(view);
        InitEvents();
    }
    @Override
    public void onResume() {
        super.onResume();

        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.windowAnimations = R.style.DialogAnimation;
                window.setAttributes(params);
            }
        }
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if (hourFragment != null) {
            getChildFragmentManager().beginTransaction()
                    .remove(hourFragment)
                    .commit();
            hourFragment = null; // Reset the reference to null
        }
    }
    private void InitViews(View v)
    {
        navBar = v.findViewById(R.id.bottom_bar);
    }
    private void InitEvents() {
        navBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                if(lastIndex == 0 && newIndex == 1)
                    replaceFragmentRightToLeft(dayFragment);
                else if(lastIndex == 0 && newIndex == 2)
                    replaceFragmentRightToLeft(weekFragment);
                else if (lastIndex == 0 && newIndex == 3)
                    replaceFragmentRightToLeft(monthFragment);
                else if(lastIndex == 1 && newIndex == 0)
                    replaceFragmentLeftToRight(hourFragment);
                else if(lastIndex == 1 && newIndex == 2)
                    replaceFragmentRightToLeft(weekFragment);
                else if(lastIndex == 1 && newIndex == 3)
                    replaceFragmentRightToLeft(monthFragment);
                else if(lastIndex == 2 &&  newIndex == 0)
                    replaceFragmentLeftToRight(hourFragment);
                else if(lastIndex == 2 && newIndex == 1)
                    replaceFragmentLeftToRight(dayFragment);
                else if(lastIndex == 2 && newIndex == 3)
                    replaceFragmentRightToLeft(monthFragment);
                else if(lastIndex == 3 && newIndex == 0)
                    replaceFragmentLeftToRight(hourFragment);
                else if(lastIndex == 3 && newIndex == 1)
                    replaceFragmentLeftToRight(dayFragment);
                else if(lastIndex == 3 && newIndex == 2)
                    replaceFragmentLeftToRight(weekFragment);

            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }
    public void replaceFragmentRightToLeft(DialogFragment fragment)
    {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ft.replace(R.id.fl_temperature, fragment);
        ft.commit();

    }
    public void replaceFragmentLeftToRight(DialogFragment fragment)
    {
        FragmentManager fm = getChildFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        ft.replace(R.id.fl_temperature, fragment);
        ft.commit();
    }
}
