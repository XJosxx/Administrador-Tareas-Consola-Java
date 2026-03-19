package servicios;

import java.time.LocalDate;
import java.util.List;

import entidades.Tarea;
import entidades.PrioridadTarea;
import entidades.Categoria;

/**
 * Contrato de casos de uso para gestion de tareas.
 */
public interface InterfazTareaServicio {
    /**
     * Crea y registra una nueva tarea.
     */
    public Tarea crearTarea(String titulo, String descripcion, LocalDate fechaLimite,
            PrioridadTarea prioridadInicial, Categoria categoriaInicial);

    /**
     * Edita los datos principales de una tarea existente.
     */
    public void editarTarea(Integer id, String titulo, String descripcion, LocalDate fechaLimite, Categoria categoria);

    /**
     * Retorna una tarea por id o lanza error si no existe.
     */
    public Tarea buscarTareaPorId(Integer id);

    /**
     * Elimina una tarea por identificador.
     */
    public void eliminarTarea(Integer id);

    /**
     * Marca una tarea como completada.
     */
    public void marcarTareaCompletada(Integer id);

    /**
     * Evalua si una tarea esta vencida y pendiente.
     */
    public boolean tareaVencida(Integer id);

    /**
     * Lista tareas con estado pendiente.
     */
    public List<Tarea> obtenerTareasPendientes();

    /**
     * Lista tareas completadas.
     */
    public List<Tarea> obtenerTareasCompletadas();

    /**
     * Lista tareas por fecha limite exacta.
     */
    public List<Tarea> obtenerTareasPorFecha(LocalDate fechaLimite);

    /**
     * Lista tareas de una categoria especifica.
     */
    public List<Tarea> obtenerTareasPorCategoria(Categoria categoria);

    /**
     * Lista tareas no completadas proximas a vencer en N dias.
     */
    public List<Tarea> obtenerTareasProximasAVencer(int dias);

    /**
     * Retorna todas las tareas registradas.
     */
    public List<Tarea> obtenerListaTareas();
}
