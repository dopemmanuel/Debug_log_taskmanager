package es.prog2425.taskmanager.modelo

open class Tarea(
    descripcion: String,
    var estado: Estado = Estado.ABIERTA,
    private val subtareas: MutableList<Tarea> = mutableListOf(),
    var usuarioAsignado: Usuario? = null
) : Actividad(descripcion, descripcion) {  // Pasamos descripción dos veces como solución temporal

    fun cambiarEstado(nuevoEstado: Estado) {
        require(estado.puedeTransicionarA(nuevoEstado)) {
            "Transición de estado no permitida de $estado a $nuevoEstado"
        }

        if (nuevoEstado == Estado.FINALIZADA && subtareas.any { it.estado != Estado.FINALIZADA }) {
            throw IllegalStateException("No se puede finalizar una tarea con subtareas pendientes")
        }

        estado = nuevoEstado
    }

    fun agregarSubtarea(subtarea: Tarea) {
        subtareas.add(subtarea)
    }

    fun asignarUsuario(usuario: Usuario) {
        usuarioAsignado = usuario
    }

    companion object {
        fun crearInstancia(descripcion: String): Tarea {
            return Tarea(descripcion)
        }
    }

    override fun toString(): String {
        return "[TAREA] ID: $id - $descripcion - Estado: $estado - ${usuarioAsignado?.nombre ?: "Sin asignar"}"
    }

    override fun obtenerDetalle(): String {
        return toString()
    }
}