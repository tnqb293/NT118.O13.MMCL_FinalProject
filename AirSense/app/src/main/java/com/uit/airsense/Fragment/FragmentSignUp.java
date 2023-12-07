package com.uit.airsense.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uit.airsense.MainActivity;
import com.uit.airsense.R;

public class FragmentSignUp extends Fragment {
    MainActivity loginActivity;
    LinearLayout layout;
    public FragmentSignUp(MainActivity activity)
    {
        this.loginActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    void InitViews(View v)
    {
        layout = v.findViewById(R.id.llSignUp);
    }
    void InitEvent()
    {
        int childCount = layout.getChildCount();
        long delayPerItem = 300 / childCount; // Tính toán thời gian delay cho mỗi item

        for (int i = 0; i < childCount; i++) {
            final View child = layout.getChildAt(i);
            child.setVisibility(View.INVISIBLE); // Ẩn item ban đầu

            // Load Animation từ tệp tin XML
            Animation animation = AnimationUtils.loadAnimation(loginActivity.getApplicationContext(), R.anim.slide_in_left);

            // Đặt thời gian delay cho mỗi item
            animation.setStartOffset(delayPerItem * i);

            // Hiển thị item và áp dụng Animation
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE); // Hiển thị item
                    child.startAnimation(animation); // Áp dụng Animation
                }
            }, delayPerItem * i); // Hiển thị mỗi item sau 1 thời gian delay
        }
    }
}
