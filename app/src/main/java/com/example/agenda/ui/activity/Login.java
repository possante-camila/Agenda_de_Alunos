package com.example.agenda.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agenda.R;
import com.example.agenda.Util.ConfiguraBd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.agenda.DAO.Usuario;


public class Login extends AppCompatActivity {

    EditText campoEmail, campoSenha;
    Button botaoAcessar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = ConfiguraBd.Firebaseautenticacao();
        inicializarComponentes();
    }

    public void validarAutentiacao(){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                Usuario usuario = new Usuario();

                usuario.setEmail(email);
                usuario.setSenha(senha);

                logar(usuario);

            } else {
                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }
    }

    private void logar(Usuario usuario) {
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // aqui tem que por a tela para ele acessar
                    abrirHome();

                    Toast.makeText(Login.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                } else {
                    // Falha no login, trate o erro aqui
                    Toast.makeText(Login.this, "Falha no login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirHome() {
        Intent i = new Intent(Login.this, MainActivity.class); //Aqui é da onde estou saindo (tela de login) para
        //onde estou indo (tela de login)
        startActivity(i);

    }

    public void login(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.email);
        campoSenha = findViewById(R.id.password);
        botaoAcessar = findViewById(R.id.buttonAcessar);
    }
}
