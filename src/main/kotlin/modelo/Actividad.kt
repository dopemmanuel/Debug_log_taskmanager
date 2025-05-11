package es.prog2425.taskmanager.modelo

import es.prog2425.taskmanager.utils.Utilidades

abstract class Actividad(protected val descripcion: String, descripcion1: String) {
    protected val id: Int
    protected val fechaCreacion: String

    init {
        require(descripcion1.isNotEmpty()) { "La descripcion debe contener texto." }
        fechaCreacion = Utilidades().obtenerFechaActual()
        id = GeneradorID().generarId(fechaCreacion)
    }

    // Muestra los detalles de la actividad
    abstract fun obtenerDetalle(): String
}