package excepciones;

public class TareaValidacionException extends TareaException {
    public TareaValidacionException(String mensaje) {
        super("Error de validacion: " + mensaje);
    }

}
