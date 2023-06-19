package com.example.agenda.ui.activity;

import com.example.agenda.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.ui.activity.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunos = new ArrayList<>();

    // ...

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
        notifyDataSetChanged();
    }

    public Aluno getAluno(int position) {
        return alunos.get(position);
    }

    // ...

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.bind(aluno);
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public class AlunoViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeTextView;
        private TextView telefoneTextView;
        private TextView emailTextView;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nome_text_view);
            telefoneTextView = itemView.findViewById(R.id.telefone_text_view);
            emailTextView = itemView.findViewById(R.id.email_text_view);
        }

        public void bind(Aluno aluno) {
            nomeTextView.setText(aluno.getNome());
            telefoneTextView.setText(aluno.getTelefone());
            emailTextView.setText(aluno.getEmail());
        }
    }
}
