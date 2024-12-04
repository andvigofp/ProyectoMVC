package mvc;

import conexion.DatbaseMYSQL;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;


public class Modelo {

    private static Connection mysql = DatbaseMYSQL.getInstance();
    private ArrayList<Autor> listaAutoes = new ArrayList<>();

    /// Método para verificar si el autor existe
    public boolean autorEncontrado(String nombreAutor) {
        // Preparar la consulta SQL
        String query = "SELECT COUNT(*) FROM libros WHERE autor = ?";

        // Variable para verificar si el autor existe
        boolean encontrado = false;

        try (PreparedStatement stmt = mysql.prepareStatement(query)) {
            // Establecer el parámetro para el nombre del autor
            stmt.setString(1, nombreAutor);

            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                // Verificar si el autor existe
                if (rs.next() && rs.getInt(1) > 0) {
                    encontrado = true;  // Autor encontrado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar errores de la base de datos
        }

        return encontrado;  // Retorna true si el autor existe, false si no
    }

    // Método para verificar si el título existe para un autor dado
    public boolean tituloLibroEncontrado(String nombreAutor, String tituloLibro) {
        // Preparar la consulta SQL
        String query = "SELECT COUNT(*) FROM libros WHERE autor = ? AND titulo = ?";

        // Variable para verificar si el título existe
        boolean encontrado = false;

        try (PreparedStatement stmt = mysql.prepareStatement(query)) {
            // Establecer los parámetros para el autor y el título
            stmt.setString(1, nombreAutor);
            stmt.setString(2, tituloLibro);

            // Ejecutar la consulta
            try (ResultSet rs = stmt.executeQuery()) {
                // Verificar si el título existe para el autor
                if (rs.next() && rs.getInt(1) > 0) {
                    encontrado = true;  // Título encontrado para el autor
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Manejar errores de la base de datos
        }

        return encontrado;  // Retorna true si el título existe para el autor, false si no
    }

    // Método para iniciar la validación
    public boolean iniciarValidacion(String nombreAutor, String tituloLibro) {
        // Verificar si el autor existe
        if (!autorEncontrado(nombreAutor) && !tituloLibroEncontrado(nombreAutor, tituloLibro)) {
            // Si el autor y el título no existen, mostrar el mensaje
            JOptionPane.showMessageDialog(null, "El autor '" + nombreAutor + "' y el título '" + tituloLibro + "' no existen.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;  // Ni el autor ni el título existen
        } else if (!autorEncontrado(nombreAutor)) {
            // Si el autor no existe, mostrar el mensaje
            JOptionPane.showMessageDialog(null, "El autor '" + nombreAutor + "' no existe.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false; // Autor no encontrado
        } else if (!tituloLibroEncontrado(nombreAutor, tituloLibro)) {
            // Si el título no corresponde al autor, mostrar el mensaje
            JOptionPane.showMessageDialog(null, "El título '" + tituloLibro + "' no corresponde al autor '" + nombreAutor + "'.", "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;  // Título no encontrado para el autor
        } else {
            // Si el autor y el título existen
            return true;  // Autor y título encontrados correctamente
        }
    }

    //Método para crear un usuario en la base de datos libros
    public void crearAutor(String nombreAutor, String tituloLibro, String paginas, String editorial) {
        final String INSERT_SQL = "INSERT INTO libros (autor, titulo, paginas, editorial) VALUES (?, ?, ?, ?)"; // Cambiar `autores` a `autor`

        try (PreparedStatement stmt = mysql.prepareStatement(INSERT_SQL)) {
            stmt.setString(1, nombreAutor);
            stmt.setString(2, tituloLibro);
            stmt.setString(3, paginas);
            stmt.setString(4, editorial);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Autor creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo crear el autor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el autor en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Método para obtener los autores por su nombre
    public ArrayList<Autor> obtenerAutoresPorNombre(String nombreAutor) {
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


    //Método para comprobar si el autor existe
    public boolean autorYaExiste(String nombreAutor) {
        // Consulta SQL para verificar si ya existe un autor con ese nombre y título
        String query = "SELECT COUNT(*) FROM libros WHERE autor = ?";

        try (PreparedStatement stmt = mysql.prepareStatement(query)) {
            stmt.setString(1, nombreAutor);
            ResultSet rs = stmt.executeQuery();

            // Verificamos el resultado
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Si el conteo es mayor que 0, ya existe un autor con ese nombre y título
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar si el autor ya existe: " + e.getMessage());

        }
        return false; // Asumimos que no existe en caso de error
    }


    //Comprobar si existe ese autor
    public boolean existeAutor(String nombreAutor) {
        String query = "SELECT COUNT(*) FROM libros WHERE autor = ?";
        try (PreparedStatement stmt = mysql.prepareStatement(query)) {
            stmt.setString(1, nombreAutor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si hay registros, el autor existe
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la existencia del autor: " + e.getMessage());
        }
        return false; // Si ocurre un error o no se encuentra, devuelve false
    }

    //Método para cambiar el título del libro
    public  void cambiarTituloLibro(String nombreAutor, String nuevoTitulo) {
        String query = "UPDATE libros SET titulo = ? WHERE autor = ?";

        try (PreparedStatement stmt = mysql.prepareStatement(query)) {
            stmt.setString(1, nuevoTitulo);
            stmt.setString(2, nombreAutor);
            int filasActualizadas = stmt.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Título actualizado correctamente.");
            } else {
                System.out.println("No se encontró el autor o el título no se actualizó.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el título: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //Método para borrar el autor de la base de datos
    public void borrarAutor(String nombreAutor) {
        // Consulta para eliminar los libros asociados al autor
        String queryEliminarLibros = "DELETE FROM libros WHERE autor = ?";

        try {
            // Eliminar los libros del autor
            try (PreparedStatement stmtLibros = mysql.prepareStatement(queryEliminarLibros)) {
                stmtLibros.setString(1, nombreAutor);
                int filasEliminadasLibros = stmtLibros.executeUpdate();
                if (filasEliminadasLibros == 0) {
                    System.out.println("No se encontraron libros para este autor.");
                } else {
                    System.out.println(filasEliminadasLibros + " libro(s) eliminados.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al intentar eliminar los libros del autor: " + e.getMessage());
            e.printStackTrace();
        }
    }





}
