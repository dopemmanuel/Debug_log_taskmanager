package iesra.prog2425.servicios

import iesra.prog2425.datos.ActividadRepository
import iesra.prog2425.datos.UsuarioRepository
import iesra.prog2425.modelo.Estado
import iesra.prog2425.modelo.Actividad
import iesra.prog2425.modelo.Tarea
import iesra.prog2425.modelo.Evento
import java.util.logging.Logger

private val logger = Logger.getLogger(ActividadService::class.java.name)

class ActividadService(
    private val actividadRepo: ActividadRepository,
    private val usuarioRepo: UsuarioRepository
) {
    // Crea un evento desde crearInstancia y lo agrega al repositorio
    fun crearEvento(descripcion: String, fecha: String, ubicacion: String): Evento {
        logger.info("Creando evento: $descripcion")
        val evento = Evento.crearInstancia(descripcion, fecha, ubicacion)
        actividadRepo.agregarEvento(evento)
        logger.info("Evento creado con ID: ${evento.id}")
        return evento
    }

    // Crea una tarea desde crearInstancia y lo agrega al repositorio
    fun crearTarea(descripcion: String): Tarea {
        val tarea = Tarea.crearInstancia(descripcion)
        actividadRepo.agregarTarea(tarea)
        return tarea
    }

    // Retorna una lista con todas las actividades
    fun listarTodas(): List<Actividad> = actividadRepo.obtenerTodos()


    fun cambiarEstadoTarea(idTarea: Int, nuevoEstado: Estado): Tarea {
        val tarea = actividadRepo.buscarPorId(idTarea) as? Tarea
            ?: throw IllegalArgumentException("ID no corresponde a una tarea")

        if (tarea.estado == nuevoEstado) {
            return tarea // No hacer nada si el estado es el mismo
        }

        tarea.cambiarEstado(nuevoEstado)
        actividadRepo.actualizar(tarea)
        return tarea
    }

    fun asignarTarea(idTarea: Int, idUsuario: Int): Tarea {
        val tarea = actividadRepo.buscarPorId(idTarea) as? Tarea
            ?: throw IllegalArgumentException("ID no corresponde a una tarea")

        val usuario = usuarioRepo.buscarPorId(idUsuario)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        if (tarea.usuarioAsignado?.id == usuario.id) {
            return tarea // Ya está asignado a este usuario
        }

        tarea.asignarUsuario(usuario)
        actividadRepo.actualizar(tarea)
        return tarea
    }

    fun buscarTareaPorId(id: Int): Tarea? {
        return actividadRepo.buscarPorId(id) as? Tarea
    }

    fun listarTareasPorUsuario(idUsuario: Int): List<Tarea> {
        return actividadRepo.obtenerTodos()
            .filterIsInstance<Tarea>()
            .filter { it.usuarioAsignado?.id == idUsuario }
    }

    fun listarTareasPorEstado(estado: Estado): List<Tarea> {
        return actividadRepo.obtenerTodos()
            .filterIsInstance<Tarea>()
            .filter { it.estado == estado }
    }
}