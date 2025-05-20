package iesra.prog2425.modelo

import iesra.prog2425.utils.Utilidades

class Evento private constructor(
    descripcion: String,
    private val fecha: String,
    private val ubicacion: String
): Actividad(descripcion, descripcion) {

    companion object {
        fun crearInstancia(descripcion: String, fecha: String, ubicacion: String) = Evento(descripcion, fecha, ubicacion)
    }

    init {
        require(Utilidades().esFechaValida(fecha)) { "\nLa fecha de tener el siguiente formato (dd-MM-yyyy)\n" }
        require(ubicacion.isNotEmpty())
    }

    override fun obtenerDetalle(): String {
        return "[EVENTO] ID: $id - $descripcion - Fecha: $fecha - Ubicación: $ubicacion"
    }
}