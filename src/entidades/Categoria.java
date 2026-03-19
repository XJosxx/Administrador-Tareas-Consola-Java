package entidades;

/**
 * Categorias funcionales para agrupar tareas.
 */
public enum Categoria {
    PERSONAL,
    ESTUDIO,
    TRABAJO,
    OTROS;

    /**
     * Convierte una opcion numerica del menu a una categoria valida.
     */
    public static Categoria desdeNumero(int opcion) {
        return switch (opcion) {
            case 1 -> PERSONAL;
            case 2 -> ESTUDIO;
            case 3 -> TRABAJO;
            case 4 -> OTROS;
            default -> throw new IllegalArgumentException("Categoria invalida.");
        };
    }
}
