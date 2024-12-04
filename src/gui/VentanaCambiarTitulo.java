package gui;

import mvc.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class VentanaCambiarTitulo extends JFrame {

    private JPanel contentPane;
    private JLabel etiquetaNuevoTitulo;
    private JTextField textoNuevoTitulo;
    private JButton btnCambiarTitulo;
    private JButton btnCancelar;
    private String nombreAutor;
    private Controlador controlador;


        public VentanaCambiarTitulo(Controlador controlador, String nombreAutor) {
            this.controlador = controlador;
            this.nombreAutor = nombreAutor;
            setTitle("Aplicación autores");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 265, 188);
            contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

            setContentPane(contentPane);
            contentPane.setLayout(null);
            setLocationRelativeTo(null);
            setResizable(false);

            etiquetaNuevoTitulo = new JLabel("Escribe el nuevo título del libro:");
            etiquetaNuevoTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
            etiquetaNuevoTitulo.setBounds(21, 22, 207, 14);
            contentPane.add(etiquetaNuevoTitulo);

            textoNuevoTitulo = new JTextField();
            textoNuevoTitulo.setBounds(21, 58, 194, 20);
            contentPane.add(textoNuevoTitulo);
            textoNuevoTitulo.setColumns(10);

            btnCambiarTitulo = new JButton("Cambiar");
            btnCambiarTitulo.setBounds(134, 111, 89, 23);
            btnCambiarTitulo.addActionListener(controlador);
            contentPane.add(btnCambiarTitulo);

            btnCancelar = new JButton("Cancelar");
            btnCancelar.setBounds(21, 111, 89, 23);
            btnCancelar.addActionListener(controlador);
            contentPane.add(btnCancelar);
        }

    public JButton getBtnCambiarTitulo() {
        return btnCambiarTitulo;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public JTextField getTextoNuevoTitulo() {
        return textoNuevoTitulo;
    }
    }

