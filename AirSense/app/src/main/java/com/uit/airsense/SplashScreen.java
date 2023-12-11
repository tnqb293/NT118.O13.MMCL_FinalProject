package com.uit.airsense;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_DELAY = 1000; // 3 giây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tạo Intent để chuyển hướng đến Activity mới
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);

                // Đóng Activity hiện tại
                finish();
            }
        }, SPLASH_DELAY);
    }
}