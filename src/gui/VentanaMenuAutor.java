package gui;

import mvc.Autor;
import mvc.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaMenuAutor extends JFrame {

    private JPanel contentPane;
    private JLabel etiquetaMenuAutor;
    private JTextPane textoNombreAutor;
    private JButton btnVerDatos;
    private JButton btnCambiarTituloLibro;
    private JButton btnBorrarAutor;
    private JButton btnCerrarValidacion;
    private JButton btnVerLibros; // Nuevo botón para ver libros
    private Controlador controlador;
    private String nombreAutor;

    public VentanaMenuAutor(Controlador controlador, String nombreAutor) {
        if (controlador == null || nombreAutor == null) {
            throw new IllegalArgumentException("El controlador o el nombre del autor no pueden ser nulos");
        }

        this.controlador = controlador;
        this.nombreAutor = nombreAutor;

        setTitle("Aplicación autores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 325, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        etiquetaMenuAutor = new JLabel("Menú del autor:");
        etiquetaMenuAutor.setFont(new Font("Tahoma", Font.BOLD, 16));
        etiquetaMenuAutor.setBounds(10, 24, 147, 14);
        contentPane.add(etiquetaMenuAutor);

        btnVerDatos = new JButton("Ver datos");
        btnVerDatos.setBounds(71, 64, 163, 23);
        btnVerDatos.addActionListener(controlador);
        contentPane.add(btnVerDatos);

        btnCambiarTituloLibro = new JButton("Cambiar título del libro");
        btnCambiarTituloLibro.setBounds(71, 98, 163, 23);
        btnCambiarTituloLibro.addActionListener(controlador);
        contentPane.add(btnCambiarTituloLibro);

        btnBorrarAutor = new JButton("Borrar autor");
        btnBorrarAutor.setBounds(71, 132, 163, 23);
        btnBorrarAutor.addActionListener(controlador);
        contentPane.add(btnBorrarAutor);

        btnCerrarValidacion = new JButton("Cerrar validación");
        btnCerrarValidacion.setBounds(150, 227, 145, 23);
        btnCerrarValidacion.addActionListener(controlador);
        contentPane.add(btnCerrarValidacion);

        // Botón para ver la lista de libros
        btnVerLibros = new JButton("Ver libros");
        btnVerLibros.setBounds(71, 164, 163, 23); // Nueva posición del botón
        btnVerLibros.addActionListener(controlador);
        contentPane.add(btnVerLibros);

        textoNombreAutor = new JTextPane();
        textoNombreAutor.setEditable(false);
        textoNombreAutor.setBounds(167, 24, 132, 20);
        textoNombreAutor.setText(nombreAutor);
        contentPane.add(textoNombreAutor);

    }

    public JButton getBtnVerDatos() {
        return btnVerDatos;
    }

    public JButton getBtnCambiarTituloLibro() {
        return btnCambiarTituloLibro;
    }

    public JButton getBtnBorrarAutor() {
        return btnBorrarAutor;
    }

    public JButton getBtnCerrarValidacion() {
        return btnCerrarValidacion;
    }

    public JButton getBtnVerLibros() {
        return btnVerLibros;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }


    public void mostrarListaLibros() {
        // Obtener los libros del autor de la base de datos
        List<Autor> libros = controlador.obtenerLibrosPorAutor(nombreAutor);

        // Crear una nueva ventana para mostrar la lista de libros
        JFrame ventanaLibros = new JFrame("Libros de " + nombreAutor);
        ventanaLibros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventanaLibros.setSize(300, 400);
        ventanaLibros.setLocationRelativeTo(null);

        // Crear un panel para la lista
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        ventanaLibros.setContentPane(panel);

        // Crear una lista de strings para mostrar los títulos de los libros
        List<String> listaTítulos = new ArrayList<>();

        // Si no hay libros, mostrar un mensaje
        if (libros == null || libros.isEmpty()) {
            listaTítulos.add("No hay libros registrados para este autor.");
        } else {
            // Si hay libros, agregar los títulos a la lista
            for (Autor libro : libros) {
                listaTítulos.add(libro.getTitulo());
            }
        }

        // Crear una lista de libros usando JList
        JList<String> listaLibros = new JList<>(listaTítulos.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(listaLibros);

        // Añadir la lista al panel
        panel.add(scrollPane, BorderLayout.CENTER);

        // Hacer visible la ventana
        ventanaLibros.setVisible(true);
    }
}