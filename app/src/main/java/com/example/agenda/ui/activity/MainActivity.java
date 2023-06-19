package com.example.agenda.ui.activity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.DAO.AlunoDAO;
import com.example.agenda.R;
import com.example.agenda.Util.ConfiguraBd;
import com.example.agenda.ui.activity.model.Aluno;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String CHAVE_ALUNO = "aluno";
    private AlunoDAO dao;
    private ArrayAdapter<Aluno> adapter;
    private ListView listaDeAlunos;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = ConfiguraBd.Firebaseautenticacao();

        dao = AlunoDAO.getInstance();

        dao.salva(new Aluno("Eduardo", "434354", "camila@gmail.com"));

        Toast.makeText(this, "Bem vindo de volta!", Toast.LENGTH_SHORT).show();

        setTitle("Cadastro de Alunos");

        FloatingActionButton botaoNovoAluno = findViewById(R.id.floatingActionButton3);
        botaoNovoAluno.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FormularioAlunoActivity.class));
            }
        });

        listaDeAlunos = findViewById(R.id.list_view_alunos);

        configuraLista();
        registerForContextMenu(listaDeAlunos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater()
                .inflate(R.menu.activity_lista_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        CharSequence tituloDoMenu = item.getTitle();
    if(itemId ==R.id.activity_lista_alunos_menu_remover){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
        remove(alunoEscolhido);
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

    private void configuraLista() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDeAlunos.setAdapter(adapter);
        listaDeAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openContextMenu(listaDeAlunos);
                return true;
            }
        });
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoEscolhido = adapter.getItem(position);
                if (alunoEscolhido != null) {
                    String nomeAluno = alunoEscolhido.getNome();
                    Intent vaiParaFormularioActivity = new Intent(MainActivity.this, FormularioAlunoActivity.class);
                    vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, alunoEscolhido);
                    startActivity(vaiParaFormularioActivity);
                }
            }
        });
    }

    private void atualizaListaAlunos() {
        List<Aluno> alunos = dao.todos();
        adapter.clear();
        adapter.addAll(alunos);
        adapter.notifyDataSetChanged();
    }

    public void deslogar(View view){
        try{
            auth.signOut();
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
