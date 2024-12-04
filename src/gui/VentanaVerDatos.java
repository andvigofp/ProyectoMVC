package gui;

import mvc.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaVerDatos extends JFrame  {

    private JPanel contentPane;
    private JLabel etiquetaDatosAutor;
    private JLabel etiquetaNombreAutor;
    private JLabel etiquetaPaginas;
    private JLabel etiquetaEditorial;
    private JTextPane datoNombreAutor;
    private JTextPane datoPaginas;
    private JTextPane datoEditorial;
    private JButton btnVolver;
    private Controlador controlador;

    public VentanaVerDatos(Controlador controlador, String nombreAutor, int paginas, String editorial) {
        this.controlador = controlador;

        setTitle("Aplicación autores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 304, 373);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        etiquetaDatosAutor = new JLabel("Datos autor");
        etiquetaDatosAutor.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaDatosAutor.setFont(new Font("Tahoma", Font.BOLD, 16));
        etiquetaDatosAutor.setBounds(64, 32, 169, 30);
        contentPane.add(etiquetaDatosAutor);

        datoNombreAutor = new JTextPane();
        datoNombreAutor.setEditable(false);
        datoNombreAutor.setBounds(64, 111, 169, 20);
        datoNombreAutor.setText(nombreAutor);
        contentPane.add(datoNombreAutor);

        etiquetaNombreAutor = new JLabel("Nombre:");
        etiquetaNombreAutor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaNombreAutor.setBounds(64, 86, 57, 14);
        contentPane.add(etiquetaNombreAutor);

        btnVolver = new JButton("Volver");
        btnVolver.setBounds(99, 278, 89, 23);
        btnVolver.addActionListener(controlador);
        contentPane.add(btnVolver);

        etiquetaPaginas = new JLabel("Páginas:");
        etiquetaPaginas.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaPaginas.setBounds(64, 142, 57, 14);
        contentPane.add(etiquetaPaginas);

        datoPaginas = new JTextPane();
        datoPaginas.setEditable(false);
        datoPaginas.setBounds(64, 167, 169, 20);
        datoPaginas.setText(String.valueOf(paginas));
        contentPane.add(datoPaginas);

        etiquetaEditorial = new JLabel("Editorial:");
        etiquetaEditorial.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaEditorial.setBounds(64, 209, 169, 14);
        contentPane.add(etiquetaEditorial);

        datoEditorial = new JTextPane();
        datoEditorial.setEditable(false);
        datoEditorial.setBounds(64, 234, 169, 20);
        datoEditorial.setText(editorial);
        contentPane.add(datoEditorial);
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void actualizarDatos(String nombreAutor, int paginas, String editorial) {
        datoNombreAutor.setText(nombreAutor); // Actualiza el JTextPane con el nombre del autor
        datoPaginas.setText(String.valueOf(paginas)); // Convierte las páginas a String y actualiza el JTextPane
        datoEditorial.setText(editorial); // Actualiza el JTextPane con la editorial
    }

    }


