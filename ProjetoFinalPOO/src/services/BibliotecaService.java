package services;

import entities.Livro;
import entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibliotecaService {
    private List<Livro> livros;
    private List<Usuario> usuarios;

    public BibliotecaService() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        inicializarDados();
    }

    private void inicializarDados() {
        // Livros iniciais
        livros.add(new Livro("L001", "Dom Casmurro", "Machado de Assis"));
        livros.add(new Livro("L002", "1984", "George Orwell"));
        livros.add(new Livro("L003", "O Senhor dos Anéis", "J.R.R. Tolkien"));

        // Usuários iniciais
        usuarios.add(new entities.Leitor("U001", "João Silva"));
        usuarios.add(new entities.Administrador("A001", "Maria Admin"));
    }

    // Gerenciamento de Livros
    public void adicionarLivro(String id, String titulo, String autor) {
        if (buscarLivroPorId(id).isPresent()) {
            throw new IllegalArgumentException("Já existe um livro com este ID");
        }
        livros.add(new Livro(id, titulo, autor));
    }

    public void removerLivro(String livroId) {
        Livro livro = buscarLivroPorId(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("Não é possível remover um livro emprestado");
        }

        livros.remove(livro);
    }

    public void atualizarLivro(String livroId, String novoTitulo, String novoAutor) {
        Livro livro = buscarLivroPorId(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        livro.setTitulo(novoTitulo);
        livro.setAutor(novoAutor);
    }

    public Optional<Livro> buscarLivroPorId(String id) {
        return livros.stream()
                .filter(livro -> livro.getId().equals(id))
                .findFirst();
    }

    public List<Livro> listarLivros() {
        return new ArrayList<>(livros);
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    // Gerenciamento de Usuários
    public Optional<Usuario> buscarUsuarioPorId(String id) {
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst();
    }

    public void adicionarUsuario(Usuario usuario) {
        if (buscarUsuarioPorId(usuario.getId()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este ID");
        }
        usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // Consultas
    public void exibirCatalogo() {
        System.out.println("\n=== CATÁLOGO DE LIVROS ===");
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado");
        } else {
            livros.forEach(System.out::println);
        }
    }

    public void exibirLivrosDisponiveis() {
        System.out.println("\n=== LIVROS DISPONÍVEIS ===");
        List<Livro> disponiveis = livros.stream()
                .filter(Livro::isDisponivel)
                .toList();

        if (disponiveis.isEmpty()) {
            System.out.println("Nenhum livro disponível no momento");
        } else {
            disponiveis.forEach(System.out::println);
        }
    }
}