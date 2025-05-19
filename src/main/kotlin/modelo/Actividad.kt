package iesra.prog2425.modelo

import iesra.prog2425.utils.Utilidades

abstract class Actividad(protected val descripcion: String, descripcion1: String) {
    val id: Int
    protected val fechaCreacion: String

    init {
        require(descripcion1.isNotEmpty()) { "La descripcion debe contener texto." }
        fechaCreacion = Utilidades().obtenerFechaActual()
        id = GeneradorID().generarId(fechaCreacion)
    }

    // Muestra los detalles de la actividad
    abstract fun obtenerDetalle(): String
}