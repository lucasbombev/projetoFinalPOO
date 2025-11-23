import entities.*;
import services.BibliotecaService;
import java.util.Scanner;

public class Main {
    private static BibliotecaService biblioteca = new BibliotecaService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE BIBLIOTECA ONLINE ===");

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> biblioteca.exibirCatalogo();
                case 3 -> biblioteca.exibirLivrosDisponiveis();
                case 4 -> realizarEmprestimo();
                case 5 -> realizarDevolucao();
                case 6 -> consultarHistorico();
                case 7 -> gerenciarLivros();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Cadastrar Usuário");
        System.out.println("2. Consultar Catálogo");
        System.out.println("3. Livros Disponíveis");
        System.out.println("4. Realizar Empréstimo");
        System.out.println("5. Realizar Devolução");
        System.out.println("6. Consultar Histórico");
        System.out.println("7. Gerenciar Livros (Admin)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarUsuario() {
        System.out.println("\n=== CADASTRAR USUÁRIO ===");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Tipo (LEITOR/ADMIN): ");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            Usuario usuario;
            if (tipo.equals("ADMIN")) {
                usuario = new Administrador(id, nome);
            } else {
                usuario = new Leitor(id, nome);
            }
            biblioteca.adicionarUsuario(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void realizarEmprestimo() {
        System.out.println("\n=== REALIZAR EMPRÉSTIMO ===");
        System.out.print("ID do Usuário: ");
        String usuarioId = scanner.nextLine();
        System.out.print("ID do Livro: ");
        String livroId = scanner.nextLine();

        try {
            Usuario usuario = biblioteca.buscarUsuarioPorId(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            Livro livro = biblioteca.buscarLivroPorId(livroId)
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

            if (usuario instanceof Leitor leitor) {
                Emprestimo emprestimo = new Emprestimo(livro, usuario);
                leitor.registrarEmprestimo(emprestimo);
            } else {
                System.out.println("Apenas leitores podem realizar empréstimos");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void realizarDevolucao() {
        System.out.println("\n=== REALIZAR DEVOLUÇÃO ===");
        System.out.print("ID do Usuário: ");
        String usuarioId = scanner.nextLine();
        System.out.print("ID do Livro: ");
        String livroId = scanner.nextLine();

        try {
            Usuario usuario = biblioteca.buscarUsuarioPorId(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            if (usuario instanceof Leitor leitor) {
                leitor.registrarDevolucao(livroId);
            } else {
                System.out.println("Apenas leitores podem realizar devoluções");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void consultarHistorico() {
        System.out.println("\n=== CONSULTAR HISTÓRICO ===");
        System.out.print("ID do Usuário: ");
        String usuarioId = scanner.nextLine();

        try {
            Usuario usuario = biblioteca.buscarUsuarioPorId(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            if (usuario instanceof Leitor leitor) {
                leitor.consultarHistorico();
            } else {
                System.out.println("Apenas leitores possuem histórico de empréstimos");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void gerenciarLivros() {
        System.out.println("\n=== GERENCIAR LIVROS (ADMIN) ===");
        System.out.print("ID do Administrador: ");
        String adminId = scanner.nextLine();

        try {
            Usuario usuario = biblioteca.buscarUsuarioPorId(adminId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            if (usuario instanceof Administrador admin) {
                System.out.println("1. Adicionar Livro");
                System.out.println("2. Remover Livro");
                System.out.println("3. Atualizar Livro");
                System.out.print("Escolha: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("ID do Livro: ");
                        String id = scanner.nextLine();
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        admin.adicionarLivro(biblioteca, id, titulo, autor);
                    }
                    case 2 -> {
                        System.out.print("ID do Livro a remover: ");
                        String livroId = scanner.nextLine();
                        admin.removerLivro(biblioteca, livroId);
                    }
                    case 3 -> {
                        System.out.print("ID do Livro a atualizar: ");
                        String livroId = scanner.nextLine();
                        System.out.print("Novo Título: ");
                        String novoTitulo = scanner.nextLine();
                        System.out.print("Novo Autor: ");
                        String novoAutor = scanner.nextLine();
                        admin.atualizarLivro(biblioteca, livroId, novoTitulo, novoAutor);
                    }
                    default -> System.out.println("Opção inválida!");
                }
            } else {
                System.out.println("Apenas administradores podem gerenciar livros");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}