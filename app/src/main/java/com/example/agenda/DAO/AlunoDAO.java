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
        Aluno alunoEncontrado = null;
        for (Aluno a : alunos) {
            if (a.getId() == aluno.getId()) {
                alunoEncontrado = a;
                break;
            }
        }
        if (alunoEncontrado != null) {
            int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoDoAluno, aluno);
        }
    }


    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }

    public void remove(Aluno aluno) {
        int id = aluno.getId();
        Aluno alunoDevolvido = buscaAlunoPeloId(id);
        if (alunoDevolvido != null) {
            alunos.remove(alunoDevolvido);
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
