package entidades;

/**
 * Niveles de prioridad disponibles para una tarea.
 */
public enum PrioridadTarea {
    BAJA(1),
    MEDIA(2),
    ALTA(3);

    private final int prioridad;

    PrioridadTarea(int prioridad) {
        this.prioridad = prioridad;
    }

    public static PrioridadTarea desdeNumero(int numero) {
        return switch (numero) {
            case 1 -> BAJA;
            case 2 -> MEDIA;
            case 3 -> ALTA;
            default -> throw new IllegalArgumentException("Prioridad invalida.");
        };
    }

    /**
     * Devuelve el valor numerico de prioridad para salida en consola.
     */
    public int getPrioridad() {
        return prioridad;
    }
}
