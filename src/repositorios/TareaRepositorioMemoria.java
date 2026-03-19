package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entidades.Tarea;

/**
 * Implementacion en memoria del repositorio de tareas.
 */
public class TareaRepositorioMemoria implements InterfazTareaRepositorio {
    private final List<Tarea> tareas = new ArrayList<>();

    /**
     * Inserta una tarea en coleccion interna.
     */
    @Override
    public void guardar(Tarea tarea) {
        tareas.add(tarea);
    }

    /**
     * Recorre la coleccion y retorna la primera coincidencia por id.
     */
    @Override
    public Optional<Tarea> buscarPorId(Integer id) {
        for (Tarea tarea : tareas) {
            if (tarea.getId().equals(id)) {
                return Optional.of(tarea);
            }
        }
        return Optional.empty();
    }

    /**
     * Remueve una tarea de la coleccion.
     */
    @Override
    public void eliminar(Tarea tarea) {
        tareas.remove(tarea);
    }

    /**
     * Retorna una vista inmutable de las tareas almacenadas.
     */
    @Override
    public List<Tarea> listarTodas() {
        return List.copyOf(tareas);
    }
}
