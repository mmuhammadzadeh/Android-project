package com.example.mymusicapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 2500; // 2.5 ثانیه

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("openAccount", true); // نشون بده که باید فرگمنت Account باز شه
            startActivity(intent);
            finish();
        }, SPLASH_TIME);
    }
}