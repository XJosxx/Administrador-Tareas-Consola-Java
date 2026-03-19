package tests;

import java.time.LocalDate;
import entidades.Categoria;
import entidades.PrioridadTarea;
import entidades.Tarea;

/**
 * Prueba manual minima de construccion y cambio de estado de Tarea.
 */
public class TareaTest {
    /**
     * Ejecuta un escenario simple para inspeccion visual en consola.
     */
    public static void main(String[] args) {
        String titulo = "Prueba1";
        String descripcion = "Tarea de prueba";
        PrioridadTarea nivel = PrioridadTarea.BAJA;
        Categoria categoria = Categoria.ESTUDIO;

        Tarea tarea1 = new Tarea(titulo, descripcion, LocalDate.now(), nivel, categoria);

        assertTrue(tarea1.getTitulo().equals(titulo), "Debe mantener el titulo");
        assertTrue(tarea1.getDescripcion().equals(descripcion), "Debe mantener la descripcion");
        assertTrue(tarea1.getCategoria() == categoria, "Debe mantener la categoria");
        assertTrue(!tarea1.getCompletada(), "Inicialmente no completada");

        tarea1.completada();
        assertTrue(tarea1.getCompletada(), "Debe quedar completada despues de completada()");

        System.out.println("TareaTest: OK");
    }

    private static void assertTrue(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

}
