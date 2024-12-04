package mvc;


import conexion.DatbaseMYSQL;
import gui.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controlador  implements ActionListener {

    private Modelo modelo;
    private Autor autor;  // Atributo para almacenar el autor
    private static Connection mysql = DatbaseMYSQL.getInstance();
    private VentanaInicioSesion ventanaInicioSesion;
    private VentanaCrearAutor ventanaCrearAutor;
    private VentanaMenuAutor ventanaMenuAutor;
    private VentanaVerDatos ventanaVerDatos;
    private VentanaCambiarTitulo ventanaCambiarTitulo;
    private VentanaBorrarAutor ventanaBorrarAutor;


    public Controlador(Modelo modelo) {
        this.modelo = modelo;
        this.ventanaInicioSesion = new VentanaInicioSesion(this);
        this.ventanaCrearAutor = new VentanaCrearAutor(this);

    }

    public void setVentanaInicioSesion(VentanaInicioSesion ventanaInicioSesion) {
        this.ventanaInicioSesion = ventanaInicioSesion;
    }

    // Método para mostar la Ventana de valiación
    public void mostrarVentanaInicioSesion() {
        // Comprobar si la ventana ya está creada, si no, crear una nueva instancia
        if (ventanaInicioSesion == null) {
            ventanaInicioSesion = new VentanaInicioSesion(this);
        }

        // Limpiar los datos y que no deje por defecto
        ventanaInicioSesion.limpiarCampos();

        // Hacer visible la ventana
        ventanaInicioSesion.setVisible(true);
    }

    //Método para mostrar la ventana de crar un autor
    public void mostrarVentanaCrearAutor() {
        if (ventanaCrearAutor == null) {
            ventanaCrearAutor = new VentanaCrearAutor(this); // Asegúrate de inicializarla
        }
        ventanaCrearAutor.setVisible(true);
    }


    //Método para mostar la ventana menu autor
    private void mostrarMenuAutor(String nombreAutor) {
        if (ventanaMenuAutor == null) {
            ventanaMenuAutor = new VentanaMenuAutor(this, nombreAutor);
        }
        ventanaMenuAutor.setVisible(true);
    }

    //Método para mostrar la ventana de ver los datos del autor
    public void mostrarVentanaVerDatos(String nombreAutor) {
        // Busca al autor en la base de datos
        ArrayList<Autor> autores = modelo.obtenerAutoresPorNombre(nombreAutor);

        if (autores != null && !autores.isEmpty()) {
            // Si la ventana no ha sido creada, inicialízala con los datos del primer autor
            if (ventanaVerDatos == null) {
                Autor autor = autores.get(0);  // Obtén el primer autor de la lista
                // Crea la ventana si no está inicializada
                ventanaVerDatos = new VentanaVerDatos(this, autor.getTitulo(), autor.getPaginas(), autor.getEditorial());
                // Asegura que el botón de volver esté correctamente asignado
                ventanaVerDatos.getBtnVolver().addActionListener(this); // Registra el evento del botón 'Volver'
            } else {
                // Si la ventana ya existe, solo actualiza los datos con el primer autor
                Autor autor = autores.get(0);  // Obtén el primer autor de la lista
                ventanaVerDatos.actualizarDatos( autor.getTitulo(), autor.getPaginas(), autor.getEditorial());
            }
            ventanaVerDatos.setVisible(true);  // Muestra la ventana
        } else {
            // Si no se encuentra el autor, muestra un mensaje en consola
            System.out.println("Autor no encontrado.");
        }
    }

    //Mostrar el la ventana de cambiar el titulo
    public void mostrarVentanaCambiarTitulo(String nombreAutor) {
        // Verificar si el autor existe en la base de datos
        ArrayList<Autor> autores = modelo.obtenerAutoresPorNombre(nombreAutor); // Método para buscar el autor

        // Comprobar si el autor fue encontrado
        if (autores != null && !autores.isEmpty()) {
            // Si la ventana no ha sido creada, inicialízala
            if (ventanaCambiarTitulo == null) {
                ventanaCambiarTitulo = new VentanaCambiarTitulo(this, nombreAutor);
                // Registrar los eventos de los botones
                ventanaCambiarTitulo.getBtnCambiarTitulo().addActionListener(this);
                ventanaCambiarTitulo.getBtnCancelar().addActionListener(this);
            }

            // Muestra la ventana de cambiar título
            ventanaCambiarTitulo.setVisible(true);
        } else {
            // Mensaje informativo si no se encuentra al autor
            JOptionPane.showMessageDialog(null, "Autor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Método para mostrar la ventana de borrar un autor
    public void mostrarVentanaBorrarAutor(String nombreAutor) {
        // Verificar si el autor existe en la base de datos
        boolean existeAutor = modelo.existeAutor(nombreAutor); // Método para verificar la existencia del autor

        // Comprobar si el autor fue encontrado
        if (existeAutor) {
            // Si la ventana no ha sido creada, inicialízala
            if (ventanaBorrarAutor == null) {
                ventanaBorrarAutor = new VentanaBorrarAutor(this, nombreAutor);
                // Registrar los eventos de los botones
                ventanaBorrarAutor.getBtnBorrar().addActionListener(this);
                ventanaBorrarAutor.getBtnCancelar().addActionListener(this);
            }

            // Muestra la ventana de borrar autor
            ventanaBorrarAutor.setVisible(true);
        } else {
            // Mensaje informativo si no se encuentra al autor
            JOptionPane.showMessageDialog(null, "Autor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Método para cerrar la sesion y cerrar todas las ventanas
    public void cerrarSesion() {

        // Cerrar y limpiar las instancias de las ventanas
        if (ventanaMenuAutor != null) ventanaMenuAutor.dispose();
        if (ventanaVerDatos != null) ventanaVerDatos.dispose();
        if (ventanaBorrarAutor != null) ventanaBorrarAutor.dispose();
        if (ventanaCambiarTitulo != null) ventanaCambiarTitulo.dispose();
        if (ventanaCrearAutor != null) ventanaCrearAutor.dispose();
        if (ventanaInicioSesion != null) ventanaInicioSesion.dispose();

        JOptionPane.showMessageDialog(null, "Cerro Sesion Correctamente.", "Cerrar Sesion", JOptionPane.INFORMATION_MESSAGE);

    }

    //Método para obtener los libros del autor de base de datos libros
    public List<Autor> obtenerLibrosPorAutor(String nombreAutor) {
        ArrayList<Autor> listaAutores = new ArrayList<>();

        try (PreparedStatement stmt = mysql.prepareStatement("SELECT * FROM libros WHERE autor = ?")) {

            stmt.setString(1, nombreAutor);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Crea un nuevo objeto Autor para cada registro
                Autor autor = new Autor(
                        rs.getString("autor"),   // Asignar el nombre del autor
                        rs.getString("titulo"),  // Asignar el título del libro
                        rs.getString("editorial"), // Asignar la editorial
                        rs.getInt("paginas")     // Asignar el número de páginas
                );

                // Agrega el autor a la lista
                listaAutores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAutores;
    }

    //Eventos a lo que hacer cada botón en sus ventanas correspodientes
    @Override
    public void actionPerformed(ActionEvent e) {
        // Ventana InicioSesion
        if (ventanaInicioSesion !=null && e.getSource() == ventanaInicioSesion.getValidar()) {
            // Lógica de validación
            String nombreAutor = ventanaInicioSesion.getAutor().getText().trim();
            String tituloLibro = ventanaInicioSesion.getTitulo().getText().trim();

            // Verifica que ambos campos estén completos
            if (nombreAutor.isEmpty() || tituloLibro.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validación unificada del nombre del autor
            if (!nombreAutor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                JOptionPane.showMessageDialog(null, "El nombre del autor debe contener solo letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
                ventanaInicioSesion.getAutor().requestFocus(); // Regresa el foco al campo correspondiente
                return;
            }

            // Verifica que el nombreAutor no contenga tildes
            if (!nombreAutor.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "El nombre del autor no puede contener tilde.", "Error", JOptionPane.ERROR_MESSAGE);
                ventanaInicioSesion.getAutor().requestFocus();
                return;
            }

            // Lógica de validación delegada a la clase AplicacionAutores
            boolean exito = modelo.iniciarValidacion(nombreAutor, tituloLibro);

            // Si la validación fue exitosa, mostrar el menú del autor
            if (exito) {
                System.out.println("Validación exitosa, mostrando menú del autor.");
                ventanaInicioSesion.dispose();
                this.mostrarMenuAutor(nombreAutor);
            } else {
                System.out.println("Validación fallida.");
            }
        }
        // Acción para crear un nuevo autor
        else if (ventanaInicioSesion !=null && e.getSource() == ventanaInicioSesion.getBtnCrearNuevoAutorLibro()) {
            this.mostrarVentanaCrearAutor();
            ventanaInicioSesion.dispose(); // Esto debe ejecutarse después
        }
        // Acción para crear el autor
        else if (ventanaCrearAutor !=null && e.getSource() == ventanaCrearAutor.getBtnCrear()) {
            if (ventanaCrearAutor == null) {
                JOptionPane.showMessageDialog(null, "La ventana de crear autor no está inicializada.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombreAutor = ventanaCrearAutor.getTextoNombreAutor().getText().trim();
            String tituloLibro = ventanaCrearAutor.getTextoTituloLibro().getText().trim();
            String paginas = ventanaCrearAutor.getTextoPaginas().getText().trim();
            String editorial = ventanaCrearAutor.getTextoEditorial().getText().trim();

            // Verificar que todos los campos estén completos
            if (nombreAutor.isEmpty() || tituloLibro.isEmpty() || paginas.isEmpty() || editorial.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar si los campos contienen solo letras
            if (!nombreAutor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                JOptionPane.showMessageDialog(null, "El nombre del autor solo debe contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica que el nombreAutor no contenga tildes
            if (!nombreAutor.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "El nombre del autor no puede contener tilde.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que el campo de páginas contenga solo números
            if (!paginas.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "El número de páginas debe ser un valor numérico.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Convertir el texto de páginas a entero para validar que no sea 0
                int paginasTexto = Integer.parseInt(paginas);
                if (paginasTexto <= 0) {
                    JOptionPane.showMessageDialog(null, "El número de páginas debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El número de páginas debe ser un valor numérico", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Lógica para guardar el nuevo autor y su libro
            try {
                ventanaCrearAutor.limpiarCampos();
                // Verificar si ya existe un autor con el mismo nombre y título
                if (modelo.autorYaExiste(nombreAutor)) {
                    // Si ya existe, mostramos un mensaje de error
                    JOptionPane.showMessageDialog(null, "Ya existe un autor con ese nombre y título de libro.", "Error", JOptionPane.ERROR_MESSAGE);
                }else {
                    ventanaCrearAutor.limpiarCampos();
                    modelo.crearAutor(nombreAutor, tituloLibro, paginas, editorial);
                    ventanaCrearAutor.dispose(); // Cierra la ventana de crear autor
                    mostrarVentanaInicioSesion(); // Asegúrate de que VentanaInicioSesion se muestre correctamente
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar el autor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Acción para crear un nuevo autor
        if (ventanaInicioSesion !=null && e.getSource() == ventanaInicioSesion.getBtnCrearNuevoAutorLibro()) {
            if (ventanaCrearAutor == null) {
                ventanaCrearAutor = new VentanaCrearAutor(this);
                ventanaCrearAutor.getBtnCancelar().addActionListener(this); // Registro de eventos
            }
            ventanaCrearAutor.setVisible(true);
        }

        // Acción para cancelar creación de autor
        else if (ventanaCrearAutor != null && e.getSource() == ventanaCrearAutor.getBtnCancelar()) {
            ventanaCrearAutor.dispose();
            ventanaCrearAutor = null; // Limpia la referencia
            this.mostrarVentanaInicioSesion(); // Regresar a la ventana de inicio sesión
        }
        // Acción para ver los datos del autor
        else if (ventanaMenuAutor !=null && e.getSource() == ventanaMenuAutor.getBtnVerDatos()) {
            if (ventanaMenuAutor != null) {
                this.mostrarVentanaVerDatos(ventanaMenuAutor.getNombreAutor());
            }
        }
        // Acción para cambiar título de libro
        else if (ventanaMenuAutor !=null && e.getSource() == ventanaMenuAutor.getBtnCambiarTituloLibro()) {
            if (ventanaMenuAutor != null) {
                this.mostrarVentanaCambiarTitulo(ventanaMenuAutor.getNombreAutor());
            }
        }
        // Acción para borrar autor
        else if (ventanaMenuAutor !=null && e.getSource() == ventanaMenuAutor.getBtnBorrarAutor()) {
            if (ventanaMenuAutor != null) {
                this.mostrarVentanaBorrarAutor(ventanaMenuAutor.getNombreAutor());
                ventanaMenuAutor.dispose();
            }
        }
        /// Acción para cerrar la sesión
        else if (ventanaMenuAutor != null && e.getSource() == ventanaMenuAutor.getBtnCerrarValidacion()) {
            // Verificar si la ventana de menú del autor ya está cerrada
            if (ventanaMenuAutor != null) {
                this.cerrarSesion();
                ventanaMenuAutor.dispose();  // Cierra la ventana de menú del autor
                ventanaMenuAutor = null;     // Asegura que la referencia se limpie correctamente
                this.mostrarVentanaInicioSesion();  // Muestra la ventana de inicio de sesión
            } else {
                System.out.println("VentanaMenuAutor ya está cerrada o no está inicializada.");
            }
        }
        // Acción para ver libros
        else if (ventanaMenuAutor !=null && e.getSource() == ventanaMenuAutor.getBtnVerLibros()) {
            if (ventanaMenuAutor != null) {
                ventanaMenuAutor.mostrarListaLibros();
            }
        }

        // Acción para volver desde la ventana de datos
        else if (ventanaVerDatos !=null && e.getSource() == ventanaVerDatos.getBtnVolver()) {
            // Verifica si la ventana existe antes de intentar usarla
            if (ventanaVerDatos != null) {
                ventanaVerDatos.dispose(); // Cierra la ventana de datos y vuelve al menú del autor
            } else {
                System.out.println("La ventana no está inicializada.");
            }
        }
        // Acción para cambiar el título
        else if (ventanaCambiarTitulo != null && e.getSource() == ventanaCambiarTitulo.getBtnCambiarTitulo()) {
            System.out.println("Botón 'Cambiar Título' presionado");  // Verifica si se entra en este bloque

            // Obtener el texto del campo de texto donde el usuario escribe el nuevo título
            String nuevotitulo = ventanaCambiarTitulo.getTextoNuevoTitulo().getText().trim();

            // Verificar si el título no está vacío
            if (!nuevotitulo.isEmpty()) {
                System.out.println("Título ingresado: " + nuevotitulo);  // Verifica el contenido ingresado

                // Llamada al modelo para cambiar el título del libro
                modelo.cambiarTituloLibro(ventanaCambiarTitulo.getNombreAutor(), nuevotitulo);
                ventanaCambiarTitulo.dispose();  // Cierra la ventana de cambio de título
                JOptionPane.showMessageDialog(null, "El título ha sido cambiado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("El título está vacío");  // Verifica si está entrando en esta condición
                // Mostrar mensaje de error si el título está vacío
                JOptionPane.showMessageDialog(null, "El título no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Acción para cancelar el cambio de título
        else if (ventanaCambiarTitulo !=null && e.getSource() == ventanaCambiarTitulo.getBtnCancelar()) {
            ventanaCambiarTitulo.dispose();
        }
        // Acción para borrar autor
        else if (ventanaBorrarAutor !=null && e.getSource() == ventanaBorrarAutor.getBtnBorrar()) {
            modelo.borrarAutor(ventanaBorrarAutor.getNombreAutor());
            ventanaBorrarAutor.dispose();
            this.mostrarVentanaInicioSesion();
            JOptionPane.showMessageDialog(null, "El autor ha sido borrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
        // Acción para cancelar el borrado de autor
        else if (ventanaBorrarAutor != null && e.getSource() == ventanaBorrarAutor.getBtnCancelar()) {
            this.mostrarMenuAutor(ventanaMenuAutor.getNombreAutor());
            ventanaBorrarAutor.dispose();
        }
    }
}
