package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatbaseMYSQL {
    private static Connection conn;
    private final String usuario = "root";
    private final String clave = "abc123.";
    private final String url = "jdbc:mysql://localhost:3306/bibliotecaMYSQL";

    // El constructor del singleton siempre debe ser privado para evitar llamadas de construcción directas con el operador `new`.
    public DatbaseMYSQL() {
        try {
            // Establecer la conexión solo si aún no está creada
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, usuario, clave);
            }
        } catch (SQLException e) {
            System.out.println("Error al abrir la conexión: " + e.toString());
        }
    }


    //El método estático que controla el acceso a la instancia
    //Singleton.
    public static Connection getInstance() {
        if(DatbaseMYSQL.conn == null) {
            new DatbaseMYSQL();
        }
        return DatbaseMYSQL.conn;
    }

    public static void cerrarConexion() {  // Cambié Connection a void porque no se necesita devolver nada
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.toString());
        }
    }
}