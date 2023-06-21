package com.example.agenda.ui.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.DAO.AlunoDAO;
import com.example.agenda.R;
import com.example.agenda.Util.ConfiguraBd;
import com.example.agenda.ui.activity.AlunoAdapter;
import com.example.agenda.ui.activity.FormularioAlunoActivity;

import com.example.agenda.ui.activity.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String CHAVE_ALUNO = "aluno";
    private AlunoDAO dao;
    private AlunoAdapter adapter;
    private RecyclerView recyclerView;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list_view_alunos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlunoAdapter();
        recyclerView.setAdapter(adapter);

        auth = ConfiguraBd.Firebaseautenticacao();

        dao = AlunoDAO.getInstance();

        dao.salva(new Aluno("Eduardo", "434354", "camila@gmail.com"));

        Toast.makeText(this, "Bem vindo de volta!", Toast.LENGTH_SHORT).show();

        setTitle("Cadastro de Alunos");

        FloatingActionButton botaoNovoAluno = findViewById(R.id.floatingActionButton3);
        botaoNovoAluno.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, FormularioAlunoActivity.class));
        });



        registerForContextMenu(recyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            AdapterView.AdapterContextMenuInfo menuInfo =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

            Aluno alunoEscolhido = adapter.getAluno(position);
            remove(alunoEscolhido);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaListaAlunos();
    }

    private void remove(Aluno aluno) {
        dao.remove(aluno);
        atualizaListaAlunos();
        Toast.makeText(this, "Aluno removido: " + aluno.getNome(), Toast.LENGTH_SHORT).show();
    }

    private void atualizaListaAlunos() {
        List<Aluno> alunos = dao.todos();
        adapter.setAlunos(alunos);
        adapter.notifyDataSetChanged();
    }

    public void deslogar(View view) {
        try {
            auth.signOut();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirFormularioAluno() {
        Intent intent = new Intent(MainActivity.this, FormularioAlunoActivity.class);
        startActivity(intent);
    }
}
