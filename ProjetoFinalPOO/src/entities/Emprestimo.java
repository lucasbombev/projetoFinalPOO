package entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private LocalDate dataDevolucaoPrevista;
    private Livro livro;
    private Usuario usuario;
    private boolean devolvido;
    private static final double MULTA_POR_DIA = 2.0;
    private static final int PRAZO_EMPRESTIMO = 14;

    public Emprestimo(Livro livro, Usuario usuario) {
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(PRAZO_EMPRESTIMO);
        this.livro = livro;
        this.usuario = usuario;
        this.devolvido = false;
    }

    // Getters
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public Livro getLivro() { return livro; }
    public Usuario getUsuario() { return usuario; }
    public boolean isDevolvido() { return devolvido; }

    public void registrarDevolucao() {
        this.dataDevolucao = LocalDate.now();
        this.devolvido = true;
        this.livro.devolver();
    }

    public double calcularMulta() {
        if (devolvido && dataDevolucao.isAfter(dataDevolucaoPrevista)) {
            long diasAtraso = ChronoUnit.DAYS.between(dataDevolucaoPrevista, dataDevolucao);
            return diasAtraso * MULTA_POR_DIA;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        String status = devolvido ? "Devolvido" : "Em empréstimo";
        String multaInfo = devolvido ? String.format(" Multa: R$ %.2f", calcularMulta()) : "";
        return String.format("Empréstimo [Livro: %s, Usuário: %s, Data Empréstimo: %s, Data Devolução Prevista: %s, %s%s]",
                livro.getTitulo(), usuario.getNome(), dataEmprestimo,
                dataDevolucaoPrevista, status, multaInfo);
    }
}