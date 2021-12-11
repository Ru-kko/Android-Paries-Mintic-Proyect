package com.example.reto;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


public class Login_Splash extends AppCompatActivity {

    CoordinatorLayout content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        content = findViewById(R.id.splash);
        Intent intent = new Intent(Login_Splash.this, MainActivity.class);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(content, View.ALPHA, 0.0f, 1.0f);
        alpha.setDuration(1000);
        alpha.start();


        new Handler().postDelayed(() -> {
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }, 1000);

    }

}