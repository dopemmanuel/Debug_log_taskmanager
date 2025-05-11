package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.ActividadRepository
import es.prog2425.taskmanager.datos.UsuarioRepository
import es.prog2425.taskmanager.modelo.Estado
import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Tarea
import es.prog2425.taskmanager.modelo.Evento

class ActividadService(
    private val actividadRepo: ActividadRepository,
    private val usuarioRepo: UsuarioRepository
) {
    // Crea un evento desde crearInstancia y lo agrega al repositorio
    fun crearEvento(descripcion: String, fecha: String, ubicacion: String): Evento {
        val evento = Evento.crearInstancia(descripcion, fecha, ubicacion)
        actividadRepo.agregarEvento(evento)
        return evento
    }

    // Crea una tarea desde crearInstancia y lo agrega al repositorio
    fun crearTarea(descripcion: String) {
        val tarea = Tarea.crearInstancia()
        actividadRepo.agregarTarea(tarea)
        return tarea
    }

    // Retorna una lista con todas las actividades
    fun listarActividades(): List<Actividad> = actividadRepo.obtenerTodos()


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

    // Nuevos métodos útiles
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