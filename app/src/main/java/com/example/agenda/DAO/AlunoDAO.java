package com.example.agenda.DAO;

import com.example.agenda.ui.activity.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static AlunoDAO instance;
    private final List<Aluno> alunos = new ArrayList<>();
    private int contadorDeIds = 1;

    private AlunoDAO() {
        // Construtor privado para evitar a criação de novas instâncias fora da classe
    }

    public static AlunoDAO getInstance() {
        if (instance == null) {
            instance = new AlunoDAO();
        }
        return instance;
    }

    public void salva(Aluno aluno) {
        aluno.setId(contadorDeIds);
        alunos.add(aluno);
        contadorDeIds++;
    }

    public void edita(Aluno aluno) {
        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);
            if (a.getId() == aluno.getId()) {
                alunos.set(i, aluno);
                break;
            }
        }
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        for (int i = 0; i < alunos.size(); i++) {
            Aluno a = alunos.get(i);
            if (a.getId() == aluno.getId()) {
                alunos.remove(i);
                break;
            }
        }
    }

    public Aluno buscaAlunoPeloId(int id) {
        for (Aluno aluno : alunos) {
            if (aluno.getId() == id) {
                return aluno;
            }
        }
        return null;
    }
}
