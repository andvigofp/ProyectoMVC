import gui.VentanaInicioSesion;
import mvc.Controlador;
import mvc.Modelo;

public class Ejecutar {
    public static void main(String[] args) {
        try {
            // Inicializamos el modelo
            Modelo modelo = new Modelo();

            // Inicializamos el controlador, pasándole el modelo
            Controlador controlador = new Controlador(modelo);

            // Inicializamos la ventana de inicio de sesión, pasándole el controlador
            VentanaInicioSesion ventanaInicioSesion = new VentanaInicioSesion(controlador);


            // Hacemos visible la ventana de inicio de sesión
            controlador.setVentanaInicioSesion(ventanaInicioSesion);

            ventanaInicioSesion.arrancar();

        } catch (Exception e) {
            // Capturamos cualquier excepción y la imprimimos para depuración
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
