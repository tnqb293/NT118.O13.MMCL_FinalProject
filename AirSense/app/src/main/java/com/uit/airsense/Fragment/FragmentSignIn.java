package com.uit.airsense.Fragment;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.uit.airsense.MainActivity;
import com.uit.airsense.R;

public class FragmentSignIn extends Fragment {
    MainActivity loginActivity;
    LinearLayout layoutSignIn;
    AppCompatButton btCreateAccount, btLogIn;
    TextInputEditText tietEmail, tietPassword;
    int flag;
    public FragmentSignIn(MainActivity activity)
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        layoutSignIn = view.findViewById(R.id.llLogin);
        int childCount = layoutSignIn.getChildCount();
        long delayPerItem = 300 / childCount; // Tính toán thời gian delay cho mỗi item

        for (int i = 0; i < childCount; i++) {
            final View child = layoutSignIn.getChildAt(i);
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitViews(view);
        InitEvent();
    }
    void InitViews(View v)
    {
        btCreateAccount = v.findViewById(R.id.btCreateAccount);
        btLogIn = v.findViewById(R.id.btLoginSignin);
        tietPassword = v.findViewById(R.id.tietPasswordSignIn);
        tietEmail = v.findViewById(R.id.tietEmailSignIn);
    }
    void InitEvent()
    {
        btLogIn.setOnClickListener(v -> {
            String email = String.valueOf(tietEmail.getText());
            String password = String.valueOf(tietPassword.getText());
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
            {
                Toast.makeText(loginActivity, "Please fill in your account", Toast.LENGTH_SHORT).show();
                tietEmail.setText(null);
                tietPassword.setText(null);
            }
            else {

            }
        });
        btCreateAccount.setOnClickListener(v -> {
            int childCount = layoutSignIn.getChildCount();
            long delayPerItem = 300 / childCount; // Tính toán thời gian delay cho mỗi item
            flag = 0;
            final int[] completedAnimations = {0};

            // Sắp xếp các item theo thứ tự ngược lại
            for (int i = childCount - 1; i >= 0; i--) {
                final View child = layoutSignIn.getChildAt(i);
                Animation animation = AnimationUtils.loadAnimation(loginActivity.getApplicationContext(), R.anim.slide_out_left);
                animation.setStartOffset(delayPerItem * (childCount - i - 1)); // Đảo ngược thứ tự delay

                // Sử dụng Listener để kiểm tra khi animation hoàn thành
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Không cần làm gì khi animation bắt đầu
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        child.setVisibility(View.INVISIBLE); // Ẩn item sau khi animation kết thúc
                        completedAnimations[0]++;

                        // Kiểm tra nếu tất cả animation đã hoàn thành trước khi chuyển Fragment
                        if (completedAnimations[0] == childCount) {
                            loginActivity.replaceFragment(loginActivity.fmSignUp); // Thay thế Fragment khi tất cả các animation đã ẩn
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // Không cần làm gì khi animation lặp lại
                    }
                });

                // Áp dụng Animation cho từng item
                child.startAnimation(animation);
            }
        });


    }

}
