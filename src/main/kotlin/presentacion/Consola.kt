package iesra.prog2425.presentacion

import iesra.prog2425.modelo.Actividad
import iesra.prog2425.modelo.Evento
import iesra.prog2425.modelo.Tarea
import iesra.prog2425.modelo.Estado
import iesra.prog2425.servicios.ActividadService
import iesra.prog2425.servicios.UsuarioService

class Consola(
    override val actividadService: ActividadService,
    override val usuarioService: UsuarioService
) : Interfaz {
    private val separator = "=============================="

    override fun mostrarMenu() {
        println(separator)
        println("=== TASK MANAGER ===")
        println("1. Crear evento")
        println("2. Crear tarea")
        println("3. Listar todas las actividades")
        println("4. Cambiar estado de tarea")
        println("5. Asignar tarea a usuario")
        println("6. Registrar nuevo usuario")
        println("7. Listar tareas por usuario")
        println("8. Listar tareas por estado")
        println("0. Salir")
        println(separator)
        print("> ")
    }

    override fun manejarOpcion(opcion: Int) {
        when (opcion) {
            1 -> crearEvento()
            2 -> crearTarea()
            3 -> listarActividades()
            4 -> manejarCambioEstado()
            5 -> manejarAsignacionTarea()
            6 -> registrarUsuario()
            7 -> listarUsuarios()
            8 -> listarTareasPorUsuario()
            9 -> listarTareasPorEstado()
            0 -> return
            else -> mostrar("Opción no válida. Intente nuevamente.")
        }
        println()
    }

    private fun crearEvento() {
        try {
            mostrarInput("Descripción del evento: ")
            val descripcion = leerString().takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("Descripción no puede estar vacía")

            mostrarInput("Fecha del evento: ")
            val fecha = leerString().takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("Fecha no puede estar vacía")

            mostrarInput("Ubicación del evento: ")
            val ubicacion = leerString().takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("Ubicación no puede estar vacía")

            val evento = actividadService.crearEvento(descripcion, fecha, ubicacion)
            mostrar("✔ Evento creado exitosamente:\n${evento.obtenerDetalle()}")
        } catch (e: Exception) {
            mostrar("✖ Error al crear evento: ${e.message}")
        }
    }

    private fun crearTarea() {
        try {
            mostrarInput("Descripción de la tarea: ")
            val descripcion = leerString().takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("Descripción no puede estar vacía")

            val tarea = actividadService.crearTarea(descripcion)
            mostrar("✔ Tarea creada exitosamente:\n${tarea.obtenerDetalle()}")
        } catch (e: Exception) {
            mostrar("✖ Error al crear tarea: ${e.message}")
        }
    }

    override fun listarActividades() {
        try {
            val actividades = actividadService.listarTodas()
            if (actividades.isEmpty()) {
                mostrar("No hay actividades registradas")
                return
            }
            mostrarActividades(actividades)
        } catch (e: Exception) {
            mostrar("✖ Error al listar actividades: ${e.message}")
        }
    }

    private fun manejarCambioEstado() {
        try {
            mostrarInput("ID de tarea: ")
            val id = leerNum().takeIf { it > 0 }
                ?: throw IllegalArgumentException("ID debe ser un número positivo")

            mostrarInput("Nuevo estado (ABIERTA, EN_PROGRESO, FINALIZADA): ")
            val estadoStr = leerString().uppercase()

            val estado = Estado.valueOf(estadoStr)
            val tarea = actividadService.cambiarEstadoTarea(id, estado)
            mostrar("✔ Estado actualizado exitosamente:\n${tarea.obtenerDetalle()}")
        } catch (e: IllegalArgumentException) {
            mostrar("✖ Error: ${e.message ?: "Estado no válido"}")
        } catch (e: Exception) {
            mostrar("✖ Error: ${e.message}")
        }
    }

    private fun manejarAsignacionTarea() {
        try {
            mostrarInput("ID de tarea: ")
            val idTarea = leerNum().takeIf { it > 0 }
                ?: throw IllegalArgumentException("ID de tarea debe ser positivo")

            mostrarInput("ID de usuario: ")
            val idUsuario = leerNum().takeIf { it > 0 }
                ?: throw IllegalArgumentException("ID de usuario debe ser positivo")

            // Corrección: Primero obtener el ID, luego buscar el usuario
            val usuario = usuarioService.obtenerUsuario(idUsuario)
                ?: throw IllegalArgumentException("Usuario no encontrado")

            val tarea = actividadService.asignarTarea(idTarea, idUsuario)
            mostrar("✔ Tarea asignada exitosamente a ${usuario.nombre}:\n${tarea.obtenerDetalle()}")
        } catch (e: Exception) {
            mostrar("✖ Error: ${e.message ?: "Error desconocido"}")
        }
    }

    private fun registrarUsuario() {
        try {
            mostrarInput("Nombre del usuario: ")
            val nombre = leerString().takeIf { it.isNotBlank() }
                ?: throw IllegalArgumentException("Nombre no puede estar vacío")

            mostrarInput("Email del usuario: ")
            val email = leerString().takeIf { it.isNotBlank() && it.contains("@") }
                ?: throw IllegalArgumentException("Email no válido")

            val usuario = usuarioService.registrarUsuario(nombre, email)
            mostrar("✔ Usuario registrado exitosamente:\nID: ${usuario.id}, Nombre: ${usuario.nombre}, Email: ${usuario.email}")
        } catch (e: Exception) {
            mostrar("✖ Error al registrar usuario: ${e.message}")
        }
    }

    private fun listarUsuarios() {
        val usuarios = usuarioService.listarUsuarios()
        usuarios.forEach { println(it) }
    }

    private fun listarTareasPorUsuario() {
        try {
            mostrarInput("ID de usuario: ")
            val idUsuario = leerNum().takeIf { it > 0 }
                ?: throw IllegalArgumentException("ID de usuario debe ser positivo")

            val tareas = actividadService.listarTareasPorUsuario(idUsuario)

            if (tareas.isEmpty()) {
                mostrar("El usuario $idUsuario no tiene tareas asignadas")
            } else {
                mostrar("Tareas asignadas al usuario $idUsuario:")
                tareas.forEach { mostrar("- ${it.obtenerDetalle()}") }
            }
        } catch (e: Exception) {
            mostrar("✖ Error: ${e.message}")
        }
    }

    private fun listarTareasPorEstado() {
        try {
            mostrarInput("Estado (ABIERTA, EN_PROGRESO, FINALIZADA): ")
            val estadoStr = leerString().uppercase()

            val estado = Estado.valueOf(estadoStr)
            val tareas = actividadService.listarTareasPorEstado(estado)

            if (tareas.isEmpty()) {
                mostrar("No hay tareas en estado $estado")
            } else {
                mostrar("Tareas en estado $estado (${tareas.size}):")
                tareas.forEach { mostrar("- ${it.obtenerDetalle()}") }
            }
        } catch (_: IllegalArgumentException) {
            mostrar("✖ Error: Estado no válido. Opciones: ABIERTA, EN_PROGRESO, FINALIZADA")
        } catch (e: Exception) {
            mostrar("✖ Error: ${e.message}")
        }
    }

    override fun leerString(): String = readlnOrNull() ?: ""

    override fun leerNum(): Int = readlnOrNull()?.toIntOrNull() ?: -1

    override fun mostrar(x: Any) = println(x)

    override fun mostrarActividades(x: List<Actividad>) {
        println(separator)
        println("LISTADO DE ACTIVIDADES".padEnd(separator.length - 2) + "Total: ${x.size}")
        println(separator)

        val eventos = x.filterIsInstance<Evento>()
        val tareas = x.filterIsInstance<Tarea>()

        if (eventos.isNotEmpty()) {
            println("\nEVENTOS (${eventos.size}):")
            eventos.forEach { println(it.obtenerDetalle()) }
        }

        if (tareas.isNotEmpty()) {
            println("\nTAREAS (${tareas.size}):")
            tareas.forEach { println(it.obtenerDetalle()) }
        }

        println(separator)
    }

    override fun mostrarInput(x: Any) = print(x)
}