package com.example.agenda.ui.activity.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.ui.activity.Login;
import com.example.agenda.R;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // Tempo de exibição da tela de splash em milissegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}
