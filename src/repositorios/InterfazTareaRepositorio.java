package repositorios;

import java.util.List;
import java.util.Optional;

import entidades.Tarea;

/**
 * Contrato de persistencia para tareas.
 */
public interface InterfazTareaRepositorio {
    /**
     * Guarda una nueva tarea en el repositorio.
     */
    void guardar(Tarea tarea);

    /**
     * Busca una tarea por identificador.
     */
    Optional<Tarea> buscarPorId(Integer id);

    /**
     * Elimina una tarea existente.
     */
    void eliminar(Tarea tarea);

    /**
     * Lista todas las tareas almacenadas.
     */
    List<Tarea> listarTodas();
}
