package es.prog2425.taskmanager.modelo

/*

class Tarea private constructor(descripcion: String): Actividad(descripcion) {
    private var estado: Estado = Estado.ABIERTA

    companion object {
        fun crearInstancia(descripcion: String) = Tarea(descripcion)
    }

    override fun obtenerDetalle(): String = super.obtenerDetalle() + "Estado: $estado"
}
*/

class Tarea(
    id: Int,
    descripcion: String,
    var estado: Estado = Estado.ABIERTA,
    private val subtareas: MutableList<Tarea> = mutableListOf(),
    var usuarioAsignado: Usuario? = null
) : Actividad(id.toString(), descripcion) {

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

    override fun toString(): String {
        return super.toString() + " - ${usuarioAsignado?.nombre ?: "Sin asignar"}"
    }

}