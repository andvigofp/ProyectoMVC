package mvc;


public class Autor {
    private String autor;
    private String titulo;
    private String editorial;
    private int paginas;



    // Constructor
    public Autor(String autor, String titulo, String editorial, int paginas) {
        this.autor = autor;
        this.titulo = titulo;
        this.editorial = editorial;
        this.paginas = paginas;
    }

    // Getters y Setters
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        if (paginas > 0) {
            this.paginas = paginas;
        } else {
            System.out.println("El número de páginas debe ser positivo.");
        }
    }

    // Método toString
    @Override
    public String toString() {
        return "Autor{" +
                "autor='" + autor + '\'' +
                ", titulo='" + titulo + '\'' +
                ", editorial='" + editorial + '\'' +
                ", paginas=" + paginas +
                '}';
    }
}

