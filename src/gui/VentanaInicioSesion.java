package gui;

import mvc.Controlador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicioSesion extends JFrame {

    private JPanel contentPane;
    private JTextField textoAutor;
    private JTextField textoTitulo;
    private JButton btnValidar;
    private JButton btnCrearNuevoAutorLibro;

    private JButton btnRestablecer; // Botón para restablecer los campos
    private Controlador controlador;

    public VentanaInicioSesion(Controlador controlador) {
        this.controlador = controlador;

        setTitle("Aplicación autores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 507, 376);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel etiquetaInicioSesion = new JLabel("Validación");
        etiquetaInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 18));
        etiquetaInicioSesion.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaInicioSesion.setBounds(97, 26, 270, 44);
        contentPane.add(etiquetaInicioSesion);

        JLabel etiquetaAutor = new JLabel("Nombre autor:");
        etiquetaAutor.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaAutor.setBounds(160, 90, 80, 14);
        contentPane.add(etiquetaAutor);

        textoAutor = new JTextField();
        textoAutor.setBounds(160, 115, 149, 20);
        contentPane.add(textoAutor);
        textoAutor.setColumns(10);

        JLabel etiquetaContraseña = new JLabel("Título del libro:");
        etiquetaContraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
        etiquetaContraseña.setBounds(160, 146, 149, 14);
        contentPane.add(etiquetaContraseña);

        textoTitulo = new JTextField();
        textoTitulo.setColumns(10);
        textoTitulo.setBounds(160, 171, 149, 20);
        contentPane.add(textoTitulo);

        btnValidar = new JButton("Validar");
        btnValidar.setBounds(176, 215, 118, 23);
        btnValidar.addActionListener(controlador);
        contentPane.add(btnValidar);

        btnCrearNuevoAutorLibro = new JButton("Crear nuevo autor");
        btnCrearNuevoAutorLibro.setBounds(10, 303, 149, 23);
        btnCrearNuevoAutorLibro.addActionListener(controlador);
        contentPane.add(btnCrearNuevoAutorLibro);

        // Agregar el botón de restablecer
        btnRestablecer = new JButton("Restablecer");
        btnRestablecer.setBounds(176 + 118 + 10, 303, 118, 23); // Coloca el botón a la derecha de los demás
        btnRestablecer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos(); // Limpiar los campos al presionar el botón
            }
        });
        contentPane.add(btnRestablecer);
    }


    public void arrancar() {
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public JTextField getAutor(){
        return textoAutor;
    }

    public JTextField getTitulo() {
        return textoTitulo;
    }

    public JButton getValidar() {
        return btnValidar;
    }

   public JButton getBtnCrearNuevoAutorLibro() {
        return btnCrearNuevoAutorLibro;
   }

    // Método para limpiar los datos que deja por defecto
    public void limpiarCampos() {
        textoAutor.setText("");
        textoTitulo.setText("");
    }
}