import controladores.TareaControlador;
import servicios.InterfazTareaServicio;
import servicios.TareaServicio;

/**
 * Punto de entrada de la aplicacion.
 * Ensambla dependencias principales y delega la ejecucion al controller.
 */
public class App {
    /**
     * Inicializa servicio y controller para arrancar el menu interactivo.
     */
    public static void main(String[] args) throws Exception {
        InterfazTareaServicio servicio = new TareaServicio();
        TareaControlador controller = new TareaControlador(servicio);

        controller.menu();
    }
}
