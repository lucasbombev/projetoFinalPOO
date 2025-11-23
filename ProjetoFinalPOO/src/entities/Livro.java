package entities;

public class Livro {
    private String id;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public Livro(String id, String titulo, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
    }

    // Getters e Setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public boolean isDisponivel() { return disponivel; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }

    // Métodos específicos
    public void emprestar() {
        if (!disponivel) {
            throw new IllegalStateException("Livro já está emprestado");
        }
        this.disponivel = false;
    }

    public void devolver() {
        this.disponivel = true;
    }

    @Override
    public String toString() {
        return String.format("Livro [ID: %s, Título: %s, Autor: %s, Disponível: %s]",
                id, titulo, autor, disponivel ? "Sim" : "Não");
    }
}