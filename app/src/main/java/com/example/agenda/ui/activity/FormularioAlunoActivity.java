package com.example.agenda.ui.activity;

import static com.example.agenda.ui.activity.MainActivity.CHAVE_ALUNO;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.DAO.AlunoDAO;
import com.example.agenda.R;
import com.example.agenda.ui.activity.model.Aluno;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.ui.activity.AlunoAdapter;



public class FormularioAlunoActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private Aluno aluno;
    private AlunoDAO dao;

    private RecyclerView recyclerView;
    private AlunoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Cadastrar");
        setContentView(R.layout.activity_formulario_aluno);

        // recyclerView = findViewById(R.id.list_view_alunos);
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlunoAdapter();
        // recyclerView.setAdapter(adapter);

        inicializacaoDosCampos();

        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salva);
        dao = AlunoDAO.getInstance(); // Utiliza o método getInstance() para obter a instância única

        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            if (aluno != null) {
                preencheCampos();
            }
        }

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = campoNome.getText().toString();
                String telefone = campoTelefone.getText().toString();
                String email = campoEmail.getText().toString();

                Aluno alunoCriado = criaAluno(nome, telefone, email);
                if (aluno != null) {
                    alunoCriado.setId(aluno.getId());
                    dao.edita(alunoCriado);
                } else {
                    dao.salva(alunoCriado);
                }

                finish();
            }
        });

    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }


    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activy_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activy_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activy_formulario_aluno_email);
    }

    @NonNull
    private Aluno criaAluno(String nome, String telefone, String email) {
        return new Aluno(nome, telefone, email);
    }
}
