package iesra.prog2425.servicios

import iesra.prog2425.datos.ActividadRepositoryImpl
import iesra.prog2425.datos.UsuarioRepository
import iesra.prog2425.modelo.Estado
import iesra.prog2425.presentacion.Consola
import iesra.prog2425.presentacion.Interfaz
import iesra.prog2425.utils.Utilidades

class GestorActividades {
    private val salida: Interfaz
    private val servicio: ActividadService
    private val usuarioService: UsuarioService

    constructor() {
        val actividadRepo = ActividadRepositoryImpl()
        val usuarioRepo = UsuarioRepository()
        this.servicio = ActividadService(actividadRepo, usuarioRepo)
        this.usuarioService = UsuarioService(usuarioRepo)
        this.salida = Consola(servicio, usuarioService)
    }

    fun menu() {
        var salir = false
        do {
            try {
                salida.mostrarMenu()
                when(salida.leerNum()) {
                    0 -> {
                        salida.mostrar("\nSaliendo del sistema...")
                        salir = true
                    }
                    1 -> manejarCrearEvento()
                    2 -> manejarCrearTarea()
                    3 -> salida.mostrarActividades(servicio.listarTodas())
                    4 -> manejarCambioEstado()
                    5 -> manejarAsignacionTarea()
                    6 -> registrarUsuario()
                    7 -> listarTareasPorUsuario()
                    8 -> listarTareasPorEstado()
                    -1 -> salida.mostrar("\n✖ Debe ingresar un número")
                    else -> salida.mostrar("\n✖ Opción no válida. Use 0-8")
                }
            } catch (_: OperationCanceledException) {
                salida.mostrar("\nOperación cancelada por el usuario")
            } catch (e: Exception) {
                salida.mostrar("\n✖ Error: ${e.message ?: "Error desconocido"}")
            }
        } while(!salir)
    }

    private fun manejarCrearEvento() {
        val evento = servicio.crearEvento(
            pedirDescripcion("evento"),
            pedirFecha(),
            pedirUbicacion()
        )
        salida.mostrar("\n✔ Evento creado:\n${evento.obtenerDetalle()}")
    }

    private fun manejarCrearTarea() {
        val tarea = servicio.crearTarea(pedirDescripcion("tarea"))
        salida.mostrar("\n✔ Tarea creada:\n${tarea.obtenerDetalle()}")
    }

    private fun manejarCambioEstado() {
        try {
            val idTarea = pedirIdTarea()
            val estado = pedirEstado()
            val tarea = servicio.cambiarEstadoTarea(idTarea, estado)
            salida.mostrar("\n✔ Estado actualizado:\n${tarea.obtenerDetalle()}")
        } catch (e: IllegalArgumentException) {
            salida.mostrar("\n✖ Error: ${e.message}")
        }
    }

    private fun manejarAsignacionTarea() {
        try {
            val idTarea = pedirIdTarea()
            val idUsuario = pedirIdUsuario()
            val tarea = servicio.asignarTarea(idTarea, idUsuario)
            salida.mostrar("\n✔ Tarea asignada:\n${tarea.obtenerDetalle()}")
        } catch (e: IllegalArgumentException) {
            salida.mostrar("\n✖ Error: ${e.message}")
        }
    }

    private fun registrarUsuario() {
        try {
            val nombre = pedirNombreUsuario()
            val email = pedirEmail()
            val usuario = usuarioService.registrarUsuario(nombre, email)
            salida.mostrar("\n✔ Usuario registrado:\nID: ${usuario.id}, Nombre: ${usuario.nombre}")
        } catch (e: IllegalArgumentException) {
            salida.mostrar("\n✖ Error: ${e.message}")
        }
    }

    private fun listarTareasPorUsuario() {
        try {
            val idUsuario = pedirIdUsuario()
            val tareas = servicio.listarTareasPorUsuario(idUsuario)
            if (tareas.isEmpty()) {
                salida.mostrar("\nNo hay tareas asignadas a este usuario")
            } else {
                salida.mostrar("\nTareas asignadas:")
                tareas.forEach { salida.mostrar("- ${it.obtenerDetalle()}") }
            }
        } catch (e: IllegalArgumentException) {
            salida.mostrar("\n✖ Error: ${e.message}")
        }
    }

    private fun listarTareasPorEstado() {
        try {
            val estado = pedirEstado()
            val tareas = servicio.listarTareasPorEstado(estado)
            if (tareas.isEmpty()) {
                salida.mostrar("\nNo hay tareas en estado ${estado.name}")
            } else {
                salida.mostrar("\nTareas en estado ${estado.name}:")
                tareas.forEach { salida.mostrar("- ${it.obtenerDetalle()}") }
            }
        } catch (e: IllegalArgumentException) {
            salida.mostrar("\n✖ Error: ${e.message}")
        }
    }

    private fun pedirDescripcion(tipo: String): String {
        while (true) {
            salida.mostrar("\nIntroduce la descripción del $tipo (o '0' para cancelar)")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            when {
                input.isEmpty() -> salida.mostrar("\n✖ La descripción no puede estar vacía")
                input == "0" -> throw OperationCanceledException()
                else -> return input
            }
        }
    }

    private fun pedirFecha(): String {
        while (true) {
            salida.mostrar("\nIntroduce la fecha (dd-MM-yyyy) o '0' para cancelar")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            when {
                input == "0" -> throw OperationCanceledException()
                !Utilidades().esFechaValida(input) -> salida.mostrar("\n✖ Formato de fecha inválido. Use dd-MM-yyyy")
                else -> return input
            }
        }
    }

    private fun pedirUbicacion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la ubicación o '0' para cancelar")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            when {
                input.isEmpty() -> salida.mostrar("\n✖ La ubicación no puede estar vacía")
                input == "0" -> throw OperationCanceledException()
                else -> return input
            }
        }
    }

    private fun pedirIdTarea(): Int {
        while (true) {
            salida.mostrar("\nIntroduce el ID de la tarea (o '0' para cancelar)")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            if (input == "0") throw OperationCanceledException()
            val id = input.toIntOrNull() ?: throw IllegalArgumentException("ID debe ser un número")
            if (id > 0) return id
            salida.mostrar("\n✖ El ID debe ser positivo")
        }
    }

    private fun pedirIdUsuario(): Int {
        while (true) {
            salida.mostrar("\nIntroduce el ID del usuario (o '0' para cancelar)")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            if (input == "0") throw OperationCanceledException()
            val id = input.toIntOrNull() ?: throw IllegalArgumentException("ID debe ser un número")
            if (id > 0) return id
            salida.mostrar("\n✖ El ID debe ser positivo")
        }
    }

    private fun pedirEstado(): Estado {
        while (true) {
            salida.mostrar("\nIntroduce el estado (ABIERTA, EN_PROGRESO, FINALIZADA) o '0' para cancelar")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim().uppercase()
            if (input == "0") throw OperationCanceledException()
            try {
                return Estado.valueOf(input)
            } catch (_: IllegalArgumentException) {
                salida.mostrar("\n✖ Estado no válido. Opciones: ABIERTA, EN_PROGRESO, FINALIZADA")
            }
        }
    }

    private fun pedirNombreUsuario(): String {
        while (true) {
            salida.mostrar("\nIntroduce el nombre del usuario (o '0' para cancelar)")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            when {
                input.isEmpty() -> salida.mostrar("\n✖ El nombre no puede estar vacío")
                input == "0" -> throw OperationCanceledException()
                else -> return input
            }
        }
    }

    private fun pedirEmail(): String {
        while (true) {
            salida.mostrar("\nIntroduce el email (o '0' para cancelar)")
            salida.mostrarInput("> ")
            val input = salida.leerString().trim()
            when {
                input.isEmpty() -> salida.mostrar("\n✖ El email no puede estar vacío")
                !input.contains("@") -> salida.mostrar("\n✖ Email no válido. Debe contener @")
                input == "0" -> throw OperationCanceledException()
                else -> return input
            }
        }
    }
}

