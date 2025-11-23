package entities;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    protected String id;
    protected String nome;
    protected String tipo;
    protected List<Emprestimo> historico;

    public Usuario(String id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.historico = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public List<Emprestimo> getHistorico() { return new ArrayList<>(historico); }

    // Métodos abstratos
    public abstract void registrarEmprestimo(Emprestimo emprestimo);
    public abstract void registrarDevolucao(String livroId);

    public void adicionarAoHistorico(Emprestimo emprestimo) {
        historico.add(emprestimo);
    }

    @Override
    public String toString() {
        return String.format("Usuário [ID: %s, Nome: %s, Tipo: %s]", id, nome, tipo);
    }
}