package servicios;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import entidades.Categoria;
import entidades.PrioridadTarea;
import entidades.Tarea;
import excepciones.TareaNoEncontradaException;
import excepciones.TareaValidacionException;
import repositorios.InterfazTareaRepositorio;
import repositorios.TareaRepositorioMemoria;

/**
 * Implementacion de casos de uso y reglas de negocio para tareas.
 */
public class TareaServicio implements InterfazTareaServicio {
    private final InterfazTareaRepositorio repositorio;
    private final Clock clock;
    private int contadorId = 1;

    /**
     * Constructor por defecto con repositorio en memoria y reloj del sistema.
     */
    public TareaServicio() {
        this(new TareaRepositorioMemoria(), Clock.systemDefaultZone());
    }

    /**
     * Constructor para pruebas con reloj controlado.
     */
    public TareaServicio(Clock clock) {
        this(new TareaRepositorioMemoria(), clock);
    }

    /**
     * Constructor principal para inyeccion de dependencias.
     */
    public TareaServicio(InterfazTareaRepositorio repositorio, Clock clock) {
        if (repositorio == null) {
            throw new IllegalArgumentException("El repositorio no puede ser nulo.");
        }
        if (clock == null) {
            throw new IllegalArgumentException("El reloj no puede ser nulo.");
        }
        this.repositorio = repositorio;
        this.clock = clock;
    }

    /**
     * Retorna la fecha actual segun el reloj configurado.
     */
    private LocalDate hoy() {
        return LocalDate.now(clock);
    }

    /**
     * Busca una tarea por id validado.
     */
    public Tarea buscarTareaPorId(Integer id) {
        validarId(id);
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new TareaNoEncontradaException(id));
    }

    /**
     * Crea una tarea nueva verificando fecha valida y asignando id secuencial.
     */
    public Tarea crearTarea(String titulo, String descripcion, LocalDate fechaLimite,
            PrioridadTarea prioridadInicial, Categoria categoriaInicial) {
        validarFechaFutura(fechaLimite);
        Tarea tarea = new Tarea(titulo, descripcion, fechaLimite, prioridadInicial, categoriaInicial);
        tarea.setId(contadorId++);
        repositorio.guardar(tarea);
        return tarea;

    }

    /**
     * Edita una tarea existente manteniendo reglas de validacion.
     */
    public void editarTarea(Integer id, String titulo, String descripcion, LocalDate fechaLimite, Categoria categoria) {
        validarId(id);
        validarFechaFutura(fechaLimite);

        Tarea tarea = repositorio.buscarPorId(id)
                .orElseThrow(() -> new TareaNoEncontradaException(id));

        tarea.asignarTitulo(titulo);
        tarea.asignarDescripcion(descripcion);
        tarea.asignarFechaLimite(fechaLimite);
        tarea.asignarCategoria(categoria);

    }

    /**
     * Elimina una tarea existente por id.
     */
    public void eliminarTarea(Integer id) {
        validarId(id);
        repositorio.buscarPorId(id).ifPresent(tarea -> {
            repositorio.eliminar(tarea);
        });
    }

    /**
     * Marca una tarea como completada.
     */
    public void marcarTareaCompletada(Integer id) {
        validarId(id);
        repositorio.buscarPorId(id).ifPresent(Tarea::completada);
    }

    /**
     * Determina si una tarea esta vencida y aun no completada.
     */
    public boolean tareaVencida(Integer id) {
        Tarea tarea = buscarTareaPorId(id);
        return tarea.getFecha().isBefore(hoy()) && !tarea.getCompletada();
    }

    /**
     * Retorna tareas con estado pendiente.
     */
    public List<Tarea> obtenerTareasPendientes() {
        List<Tarea> listaTareas = repositorio.listarTodas();
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (!tarea.getCompletada()) {
                resultado.add(tarea);
            }
        }
        return List.copyOf(resultado);
    }

    /**
     * Retorna tareas con estado completado.
     */
    public List<Tarea> obtenerTareasCompletadas() {
        List<Tarea> listaTareas = repositorio.listarTodas();
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.getCompletada()) {
                resultado.add(tarea);
            }
        }
        return List.copyOf(resultado);
    }

    /**
     * Filtra tareas por fecha exacta.
     */
    public List<Tarea> obtenerTareasPorFecha(LocalDate fechaLimite) {
        if (fechaLimite == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }
        List<Tarea> listaTareas = repositorio.listarTodas();
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.getFecha().equals(fechaLimite)) {
                resultado.add(tarea);
            }
        }
        return List.copyOf(resultado);
    }

    /**
     * Filtra tareas por categoria.
     */
    public List<Tarea> obtenerTareasPorCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoria no puede ser nula.");
        }
        List<Tarea> listaTareas = repositorio.listarTodas();
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea tarea : listaTareas) {
            if (tarea.getCategoria().equals(categoria)) {
                resultado.add(tarea);
            }
        }
        return List.copyOf(resultado);
    }

    /**
     * Filtra tareas pendientes dentro de una ventana de dias.
     */
    public List<Tarea> obtenerTareasProximasAVencer(int dias) {
        if (dias < 0) {
            throw new IllegalArgumentException("El numero de dias no puede ser negativo.");
        }
        List<Tarea> listaTareas = repositorio.listarTodas();
        List<Tarea> resultado = new ArrayList<>();
        LocalDate inicio = hoy();
        LocalDate fin = inicio.plusDays(dias);
        for (Tarea tarea : listaTareas) {
            LocalDate fecha = tarea.getFecha();
            boolean dentroDeRango = !fecha.isBefore(inicio) && !fecha.isAfter(fin);
            if (!tarea.getCompletada() && dentroDeRango) {
                resultado.add(tarea);
            }
        }
        return List.copyOf(resultado);
    }

    /**
     * Retorna listado completo de tareas.
     */
    public List<Tarea> obtenerListaTareas() {
        return repositorio.listarTodas();
    }

    /**
     * Valida que el id sea positivo y no nulo.
     */
    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new TareaValidacionException("ID invalido. Debe ser un numero positivo.");
        }
    }

    private void validarFechaFutura(LocalDate fecha) {
        if (fecha == null || fecha.isBefore(hoy())) {
            throw new TareaValidacionException("La fecha limite debe ser futura.");
        }
    }
}
