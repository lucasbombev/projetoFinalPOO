package entities;

import services.BibliotecaService;

public class Administrador extends Usuario {

    public Administrador(String id, String nome) {
        super(id, nome, "ADMIN");
    }

    @Override
    public void registrarEmprestimo(Emprestimo emprestimo) {
        System.out.println("Administradores não realizam empréstimos");
    }

    @Override
    public void registrarDevolucao(String livroId) {
        System.out.println("Administradores não realizam devoluções");
    }

    public void adicionarLivro(BibliotecaService biblioteca, String id, String titulo, String autor) {
        biblioteca.adicionarLivro(id, titulo, autor);
        System.out.println("Livro adicionado com sucesso!");
    }

    public void removerLivro(BibliotecaService biblioteca, String livroId) {
        biblioteca.removerLivro(livroId);
        System.out.println("Livro removido com sucesso!");
    }

    public void atualizarLivro(BibliotecaService biblioteca, String livroId, String novoTitulo, String novoAutor) {
        biblioteca.atualizarLivro(livroId, novoTitulo, novoAutor);
        System.out.println("Livro atualizado com sucesso!");
    }
}