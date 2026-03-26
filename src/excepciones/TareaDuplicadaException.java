package excepciones;

public class TareaDuplicadaException extends TareaException {
    public TareaDuplicadaException(String titulo) {
        super("Tarea con titulo: '" + titulo + "' ya existe.");
    }

}
