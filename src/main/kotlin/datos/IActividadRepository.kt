package iesra.prog2425.datos

import iesra.prog2425.modelo.Actividad
import iesra.prog2425.modelo.Evento
import iesra.prog2425.modelo.Tarea

interface IActividadRepository {
    fun agregarEvento(evento: Evento)
    fun agregarTarea(tarea: Tarea)
    fun obtenerActividades(): List<Actividad>
    fun actualizar(actividad: Actividad)
    fun buscarPorId(id: Int): Actividad?
    fun obtenerTodos(): List<Actividad>
}