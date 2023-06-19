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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;


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

    public void validarAutenticacao(){
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
                    // Se der certo o  login ele vai la pra tela principal
                    abrirHome();

                    Toast.makeText(Login.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                } else {
                    // Se falhar o login
                    String excecao ="";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuario nao cadastrado, contate o administrador";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email ou senha incorretos";
                    } catch  (Exception e){
                        excecao = "Erro ao logar, contate o suporte" + e.getMessage();
                                e.printStackTrace();
                    }

                    Toast.makeText(Login.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirHome() {
        Intent i = new Intent(Login.this, MainActivity.class); //Aqui é da onde estou saindo (tela de login) para
        //onde estou indo (tela de login)
        startActivity(i);

    }

    public void login(View v) {
        validarAutenticacao();
    }


    @Override
    protected void onStart(){
        super.onStart();
    FirebaseUser usuarioAuth = auth.getCurrentUser();
    if(usuarioAuth != null){
        abrirHome();
    }
    }

    private void inicializarComponentes(){
        campoEmail = findViewById(R.id.email);
        campoSenha = findViewById(R.id.password);
        botaoAcessar = findViewById(R.id.buttonAcessar);
    }
}
