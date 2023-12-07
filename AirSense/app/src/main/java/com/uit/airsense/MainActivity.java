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

public class MainActivity extends AppCompatActivity {
    TextSwitcher textSwitcher;
    public Fragment fmSignUp, fmSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textSwitcher = findViewById(R.id.tsSignIn);
        InitVars();
        replaceFragment(fmSignIn);
//        textSwitcher.setFactory(() -> {
//            TextView textView = new TextView(this);
//            textView.setLayoutParams(new TextSwitcher.LayoutParams(
//                    TextSwitcher.LayoutParams.MATCH_PARENT,
//                    TextSwitcher.LayoutParams.WRAP_CONTENT));
//            textView.setTextSize(48);
//            textView.setTypeface(null, Typeface.BOLD);
//            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
//            textView.setShadowLayer(4, 0, 4, ContextCompat.getColor(this, R.color.black));
//            return textView;
//        });
//        textSwitcher.setText("SIGN IN");
    }
    private void InitVars()
    {
        fmSignIn = new FragmentSignIn(this);

    }
    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        if (fragment == fmSignUp)
        {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else if(fragment == fmSignIn)
        {
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        }
//        else if (fragment == reset_password_find_username)
//        {
//            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
//        }
//        else if (fragment == reset_password)
//        {
//            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
//        }
        ft.replace(R.id.flScreenLogin, fragment);
        ft.commit();
    }
}