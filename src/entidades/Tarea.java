package entidades;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad de dominio que representa una tarea y sus invariantes principales.
 */
public class Tarea {
    private Integer id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaLimite;
    private PrioridadTarea prioridad;
    private Categoria categoria;
    private boolean completada;

    /**
     * Crea una tarea valida con estado inicial pendiente.
     */
    public Tarea(String titulo, String descripcion, LocalDate fechaLimite, PrioridadTarea prioridadInicial,
            Categoria categoriaInicial) {
        if (prioridadInicial == null) {
            throw new IllegalArgumentException("La prioridad no puede ser nula.");
        }
        if (fechaLimite == null) {
            throw new IllegalArgumentException("La fecha limite no puede ser nula.");
        }
        if (categoriaInicial == null) {
            throw new IllegalArgumentException("La categoria no puede ser nula.");
        }
        asignarTitulo(titulo);
        asignarDescripcion(descripcion);
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridadInicial;
        this.categoria = categoriaInicial;
        this.completada = false;
    }

    /**
     * Indica si la tarea ya fue completada.
     */
    public boolean getCompletada() {
        return completada;
    }

    /**
     * Retorna el titulo actual.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Retorna la descripcion inicial.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Retorna el identificador unico.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retorna la prioridad asignada.
     */
    public PrioridadTarea getPrioridadTarea() {
        return this.prioridad;
    }

    /**
     * Retorna la fecha limite actual.
     */
    public LocalDate getFecha() {
        return fechaLimite;
    }

    /**
     * Retorna la categoria actual.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Asigna un titulo valido no nulo y no vacio.
     */
    public void asignarTitulo(String t) {
        if (t == null || t.isBlank()) {
            throw new IllegalArgumentException("Titulo invalido.");
        }
        this.titulo = t;
    }

    /**
     * Marca la tarea como completada.
     */
    public void completada() {
        this.completada = true;
    }

    /**
     * Asigna el id una unica vez para preservar identidad.
     */
    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id debe ser mayor a cero.");
        }
        if (this.id != null) {
            throw new IllegalStateException("El id ya fue asignado.");
        }
        this.id = id;
    }

    /**
     * Asigna una descripcion valida no nula y no vacia.
     */
    public void asignarDescripcion(String d) {
        if (d == null || d.isBlank()) {
            throw new IllegalArgumentException("Descripcion invalida.");
        }
        this.descripcion = d;
    }

    /**
     * Cambia la categoria de la tarea con validacion.
     */
    public void asignarCategoria(Categoria c) {
        if (c == null) {
            throw new IllegalArgumentException("Categoria invalida.");
        }
        this.categoria = c;
    }

    /**
     * Cambia la fecha limite con validacion basica.
     */
    public void asignarFechaLimite(LocalDate fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha invalida.");
        }
        this.fechaLimite = fecha;
    }

    @Override
    public String toString() {
        return "Tarea{titulo='" + titulo + "'\n" +
                "id=" + id + "\n" +
                "descripcion='" + descripcion + "'\n" +
                "fecha limite= " + fechaLimite + "\n" +
                "prioridad=" + prioridad.getPrioridad() + "\n" +
                "categoria=" + categoria + "\n" +
                "completada=" + completada + "}" + "\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Tarea u = (Tarea) o;

        return Objects.equals(id, u.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
