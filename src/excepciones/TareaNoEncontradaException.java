package excepciones;

public class TareaNoEncontradaException extends TareaException {
    public TareaNoEncontradaException(Integer id) {
        super("Tarea con ID:" + id + " no encontrada.");
    }

}
