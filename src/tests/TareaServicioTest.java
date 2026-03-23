package tests;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import entidades.Categoria;
import entidades.PrioridadTarea;
import entidades.Tarea;
import servicios.TareaServicio;

/**
 * Pruebas tecnicas basicas del servicio sin framework externo.
 */
public class TareaServicioTest {
    /**
     * Ejecuta escenarios principales de negocio y validacion.
     */
    public static void main(String[] args) {
        /*
         * Para pruebas deterministas, usamos un reloj fijo que siempre retorna la misma
         * fecha.
         * Esto nos permite validar reglas de negocio relacionadas con fechas sin
         * depender del tiempo real.
         */
        Clock relojFijo = Clock.fixed(Instant.parse("2026-03-18T10:00:00Z"), ZoneId.of("UTC"));
        TareaServicio servicio = new TareaServicio(relojFijo);

        /*
         * Escenario principal: Crear tareas, marcar completada, filtrar por fecha y
         * categoria, validar restricciones.
         */
        Tarea t1 = servicio.crearTarea("API", "Terminar endpoints", LocalDate.of(2026, 3, 19),
                PrioridadTarea.ALTA, Categoria.TRABAJO);
        Tarea t2 = servicio.crearTarea("Repaso", "Estudiar estructuras", LocalDate.of(2026, 3, 20),
                PrioridadTarea.MEDIA, Categoria.ESTUDIO);

        assertTrue(servicio.obtenerListaTareas().size() == 2, "Debe crear dos tareas");
        assertTrue(t1.getId() == 1 && t2.getId() == 2, "Debe asignar ids consecutivos");

        servicio.marcarTareaCompletada(t1.getId());
        assertTrue(servicio.obtenerTareasCompletadas().size() == 1, "Debe listar 1 completada");
        assertTrue(servicio.obtenerTareasPendientes().size() == 1, "Debe listar 1 pendiente");

        List<Tarea> porFecha = servicio.obtenerTareasPorFecha(LocalDate.of(2026, 3, 20));
        assertTrue(porFecha.size() == 1 && porFecha.get(0).getId().equals(t2.getId()), "Filtro por fecha invalido");

        List<Tarea> porCategoria = servicio.obtenerTareasPorCategoria(Categoria.ESTUDIO);
        assertTrue(porCategoria.size() == 1 && porCategoria.get(0).getId().equals(t2.getId()),
                "Filtro por categoria invalido");

        List<Tarea> proximas = servicio.obtenerTareasProximasAVencer(2);
        assertTrue(proximas.size() == 1 && proximas.get(0).getId().equals(t2.getId()),
                "Filtro proximas a vencer invalido");

        assertThrows(() -> servicio.crearTarea("Atrasada", "No valida", LocalDate.of(2026, 3, 17),
                PrioridadTarea.BAJA, Categoria.PERSONAL), "Debe bloquear fecha pasada");
        assertThrows(() -> servicio.obtenerListaTareas().add(t1), "Lista debe ser inmodificable");

        System.out.println("TareaServicioTest: OK");

        // --- Prueba de Idempotencia ---

        // 1. Reintentar completar una tarea que ya está completada
        servicio.marcarTareaCompletada(t1.getId()); // No debe lanzar excepción
        assertTrue(t1.getCompletada(), "t1 debe seguir completada");

        // 2. Eliminar una tarea por primera vez
        servicio.eliminarTarea(t2.getId());
        assertTrue(servicio.obtenerListaTareas().size() == 1, "Debe quedar solo 1 tarea");

        // 3. REINTENTAR eliminar la misma tarea (Idempotencia pura)
        servicio.eliminarTarea(t2.getId()); // Aquí es donde antes explotaba, ahora debe pasar directo
        assertTrue(servicio.obtenerListaTareas().size() == 1, "Debe seguir habiendo 1 tarea sin lanzar error");

        System.out.println("Pruebas de Idempotencia: OK");

    }

    /**
     * Verifica una condicion booleana y falla con mensaje descriptivo.
     */
    private static void assertTrue(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    /**
     * Verifica que una accion lance una excepcion en tiempo de ejecucion.
     */
    private static void assertThrows(Runnable accion, String mensaje) {
        try {
            accion.run();
            throw new AssertionError(mensaje);
        } catch (RuntimeException e) {
            // comportamiento esperado
        }
    }
}
