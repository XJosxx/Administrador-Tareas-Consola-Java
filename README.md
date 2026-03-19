
# Sistema de Tareas

## descripción

Sistema de consola en Java que permite gestionar tareas mediante operaciones CRUD, incluyendo seguimiento por estado, prioridad, fecha y categoría.

## Description

Console-based task management system built in Java, implementing CRUD operations with support for status, priority, date, and category tracking.

## Aprendizaje

- Aplicación de principios básicos de POO.
- Separación de responsabilidades en capas (controlador, servicio, repositorio).
- Implementación de lógica de negocio desacoplada.
- Mejora en organización y estructura del código.

## Funcionalidades

- Crear, editar, listar y eliminar tareas.
- Marcar tareas como completadas.
- Filtrar por estado (pendientes/completadas), fecha, categoria y proximas a vencer por N dias.

## Arquitectura

Arquitectura por capas:

- Controladores: manejo de entrada/salida por consola.
- Servicios: lógica de negocio y validaciones.
- Repositorios: acceso a datos (en memoria).
- Entidades: modelo de dominio (`Tarea`, `PrioridadTarea`, `Categoria`).

Flujo: Controller → Servicio → Repositorio

## Estructura

```
src/
  App.java
  controladores/
  entidades/
  servicios/
  repositorios/
  tests/
bin/
```

## Ejecucion

Compilar:

```powershell
cd SistemaTareasV2\SistemaTareas
New-Item -ItemType Directory -Force .\bin | Out-Null
$files = Get-ChildItem -Recurse -Filter *.java .\src | ForEach-Object { $_.FullName }
javac -d .\bin $files
```

Ejecutar app:

```powershell
java -cp .\bin App
```

Ejecutar pruebas:

```powershell
java -cp .\bin tests.TareaServicioTest
java -cp .\bin tests.TareaTest
```

