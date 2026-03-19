package controladores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import entidades.Categoria;
import entidades.PrioridadTarea;
import entidades.Tarea;
import servicios.InterfazTareaServicio;

/**
 * Capa de presentacion por consola para interactuar con el sistema.
 */
public class TareaControlador {
    private final InterfazTareaServicio servicio;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructor con inyeccion del servicio de casos de uso.
     */
    public TareaControlador(InterfazTareaServicio servicio) {
        this.servicio = servicio;
    }

    /**
     * Menu principal de operacion de la aplicacion.
     */
    public void menu() {
        int opcion;

        do {
            System.out.println("=====MENU====");
            System.out.println("1.Crear Tarea");
            System.out.println("2.Editar Tarea");
            System.out.println("3.Listar Tareas");
            System.out.println("4.Eliminar Tarea");
            System.out.println("5.Marcar Tarea completada");
            System.out.println("6.Listar pendientes");
            System.out.println("7.Listar completadas");
            System.out.println("8.Filtrar por fecha");
            System.out.println("9.Filtrar por categoria");
            System.out.println("10.Proximas a vencer");
            System.out.println("0. Salir");

            opcion = leerEntero("Seleccione opcion: ");

            switch (opcion) {
                case 0:
                    System.out.println("Saliendo.");
                    scanner.close();
                    break;
                case 1:
                    crearTarea();
                    break;
                case 2:
                    editarTarea();
                    break;
                case 3:
                    listarTarea();
                    break;
                case 4:
                    eliminarTarea();
                    break;
                case 5:
                    marcarTareaCompletada();
                    break;
                case 6:
                    listarPendientes();
                    break;
                case 7:
                    listarCompletadas();
                    break;
                case 8:
                    filtrarPorFecha();
                    break;
                case 9:
                    filtrarPorCategoria();
                    break;
                case 10:
                    listarProximasAVencer();
                    break;
                default:
                    System.out.println("ERROR (VALOR INVALIDO).\n");
                    break;
            }
        } while (opcion != 0);
    }

    /**
     * Flujo de creacion de tarea con captura de datos por consola.
     */
    private void crearTarea() {
        System.out.println("\n==CREAR TAREA==");

        String titulo = leerTextoNoVacio("Titulo de tarea: ");
        String descripcion = leerTextoNoVacio("Descripcion de tarea: ");
        LocalDate fecha = leerFecha("Fecha limite (dd/MM/yyyy): ");
        PrioridadTarea prioridad = leerPrioridad();
        Categoria categoria = leerCategoria();

        try {
            servicio.crearTarea(titulo, descripcion, fecha, prioridad, categoria);
            System.out.println("Creado con exito.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo crear: " + e.getMessage() + "\n");
        }
    }

    /**
     * Flujo de edicion de tarea existente.
     */
    private void editarTarea() {
        int id = leerEntero("Escriba id: ");
        String titulo = leerTextoNoVacio("Titulo de tarea: ");
        String descripcion = leerTextoNoVacio("Descripcion de tarea: ");
        LocalDate fecha = leerFecha("Fecha limite (dd/MM/yyyy): ");
        Categoria categoria = leerCategoria();

        try {
            servicio.editarTarea(id, titulo, descripcion, fecha, categoria);
            System.out.println("Editado con exito.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo editar: " + e.getMessage() + "\n");
        }
    }

    /**
     * Muestra todas las tareas registradas.
     */
    private void listarTarea() {
        System.out.println("\n==LISTADO DE TAREAS==");
        imprimirTareas(servicio.obtenerListaTareas());
    }

    /**
     * Elimina una tarea por id.
     */
    private void eliminarTarea() {
        System.out.print("\n==ELIMINAR TAREA==\n");
        int id = leerEntero("Escriba id: ");
        try {
            servicio.eliminarTarea(id);
            System.out.println("Eliminado con exito.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo eliminar: " + e.getMessage() + "\n");
        }
    }

    /**
     * Marca una tarea como completada.
     */
    private void marcarTareaCompletada() {
        int id = leerEntero("Escriba id: ");
        try {
            servicio.marcarTareaCompletada(id);
            System.out.println("Marcado con exito.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo marcar: " + e.getMessage() + "\n");
        }
    }

    /**
     * Lista tareas pendientes.
     */
    private void listarPendientes() {
        System.out.println("\n==PENDIENTES==");
        imprimirTareas(servicio.obtenerTareasPendientes());
    }

    /**
     * Lista tareas completadas.
     */
    private void listarCompletadas() {
        System.out.println("\n==COMPLETADAS==");
        imprimirTareas(servicio.obtenerTareasCompletadas());
    }

    /**
     * Filtra tareas por fecha exacta.
     */
    private void filtrarPorFecha() {
        LocalDate fecha = leerFecha("Filtrar fecha (dd/MM/yyyy): ");
        System.out.println("\n==TAREAS POR FECHA==");
        imprimirTareas(servicio.obtenerTareasPorFecha(fecha));
    }

    /**
     * Filtra tareas por categoria.
     */
    private void filtrarPorCategoria() {
        Categoria categoria = leerCategoria();
        System.out.println("\n==TAREAS POR CATEGORIA==");
        imprimirTareas(servicio.obtenerTareasPorCategoria(categoria));
    }

    /**
     * Lista tareas pendientes proximas a vencer en N dias.
     */
    private void listarProximasAVencer() {
        int dias = leerEntero("Dias hacia adelante: ");
        try {
            System.out.println("\n==PROXIMAS A VENCER==");
            imprimirTareas(servicio.obtenerTareasProximasAVencer(dias));
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo filtrar: " + e.getMessage() + "\n");
        }
    }

    /**
     * Muestra una lista de tareas o mensaje vacio.
     */
    private void imprimirTareas(List<Tarea> tareas) {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar.\n");
            return;
        }
        for (Tarea tarea : tareas) {
            System.out.println(tarea);
        }
    }

    /**
     * Lee texto no vacio desde consola.
     */
    private String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine();
            if (!valor.isBlank()) {
                return valor;
            }
            System.out.println("Entrada invalida, no puede estar vacio.");
        }
    }

    /**
     * Lee un entero valido desde consola.
     */
    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine();
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero valido.");
            }
        }
    }

    /**
     * Lee una fecha valida en formato dd/MM/yyyy.
     */
    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String fechaInput = scanner.nextLine();
            try {
                return LocalDate.parse(fechaInput, formato);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha invalida, use formato dd/MM/yyyy.");
            }
        }
    }

    /**
     * Lee y traduce prioridad numerica a enum.
     */
    private PrioridadTarea leerPrioridad() {
        while (true) {
            int nivel = leerEntero("Prioridad (1=BAJA, 2=MEDIA, 3=ALTA): ");
            try {
                return switch (nivel) {
                    case 1 -> PrioridadTarea.BAJA;
                    case 2 -> PrioridadTarea.MEDIA;
                    case 3 -> PrioridadTarea.ALTA;
                    default -> throw new IllegalArgumentException("Opcion no valida.");
                };
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Lee y traduce categoria numerica a enum.
     */
    private Categoria leerCategoria() {
        while (true) {
            System.out.println("Categoria: 1=PERSONAL, 2=ESTUDIO, 3=TRABAJO, 4=OTROS");
            int opcion = leerEntero("Seleccione categoria: ");
            try {
                return Categoria.desdeNumero(opcion);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
