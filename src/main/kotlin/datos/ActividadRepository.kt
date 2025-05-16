package es.prog2425.taskmanager.datos

import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.modelo.Evento
import es.prog2425.taskmanager.modelo.Tarea


// En datos/ActividadRepository.kt
abstract class ActividadRepository: IActividadRepository {
    protected val listaActividades: MutableList<Actividad> = mutableListOf()

    override fun agregarEvento(evento: Evento) {
        listaActividades.add(evento)
    }

    override fun agregarTarea(tarea: Tarea) {
        listaActividades.add(tarea)
    }

    override fun obtenerActividades(): List<Actividad> = listaActividades
}

// Nueva clase en el mismo archivo o en uno nuevo
class ActividadRepositoryImpl : ActividadRepository() {
    override fun actualizar(actividad: Actividad) {
        val index = listaActividades.indexOfFirst { it.id == actividad.id }
        if (index != -1) {
            listaActividades[index] = actividad
        }
    }

    override fun buscarPorId(id: Int): Actividad? {
        return listaActividades.find { it.id == id }
    }

    override fun obtenerTodos(): List<Actividad> = listaActividades
}