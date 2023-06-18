package com.example.agenda.ui.activity.model;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.ui.activity.Login;
import com.example.agenda.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 2000; // Tempo de exibição da tela de splash em milissegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Login.class); // Redireciona para a tela de login
                startActivity(intent);
                finish(); // Finaliza a SplashActivity para que ela não seja retornada quando o usuário pressionar o botão de voltar
            }
        }, SPLASH_DELAY);
    }
}
