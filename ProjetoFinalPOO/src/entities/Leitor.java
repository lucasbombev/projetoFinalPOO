package entities;

public class Leitor extends Usuario {

    public Leitor(String id, String nome) {
        super(id, nome, "LEITOR");
    }

    @Override
    public void registrarEmprestimo(Emprestimo emprestimo) {
        if (emprestimo.getLivro().isDisponivel()) {
            emprestimo.getLivro().emprestar();
            adicionarAoHistorico(emprestimo);
            System.out.println("Empréstimo registrado com sucesso!");
        } else {
            System.out.println("Livro não disponível para empréstimo");
        }
    }

    @Override
    public void registrarDevolucao(String livroId) {
        for (Emprestimo emp : getHistorico()) {
            if (emp.getLivro().getId().equals(livroId) && !emp.isDevolvido()) {
                emp.registrarDevolucao();
                System.out.println("Devolução registrada com sucesso!");
                return;
            }
        }
        System.out.println("Empréstimo não encontrado ou já devolvido");
    }

    public void consultarHistorico() {
        System.out.println("\n=== HISTÓRICO DE EMPRÉSTIMOS ===");
        if (historico.isEmpty()) {
            System.out.println("Nenhum empréstimo realizado");
        } else {
            for (Emprestimo emp : historico) {
                System.out.println(emp);
            }
        }
    }
}