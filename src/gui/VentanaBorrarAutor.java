package gui;


import mvc.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaBorrarAutor extends JFrame  {

    private JPanel contentPane;
    private JLabel etiquetaBorrarAutor;
    private JButton btnBorrar;
    private JButton btnCancelar;
    private String nombreAutor;
    private Controlador controlador;

    public VentanaBorrarAutor(Controlador controlador, String nombreAutor) {
        this.controlador = controlador;
        this.nombreAutor = nombreAutor;
        setTitle("Aplicación autores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 316, 147);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        etiquetaBorrarAutor = new JLabel("¿Está seguro de que quiere borrar este autor?");
        etiquetaBorrarAutor.setFont(new Font("Tahoma", Font.BOLD, 12));
        etiquetaBorrarAutor.setBounds(10, 11, 296, 34);
        contentPane.add(etiquetaBorrarAutor);

        btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(170, 56, 89, 23);
        btnBorrar.addActionListener(controlador);
        contentPane.add(btnBorrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(35, 56, 89, 23);
        btnCancelar.addActionListener(controlador);
        contentPane.add(btnCancelar);
    }

    public JButton getBtnBorrar() {
        return btnBorrar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }
}