package iesra.prog2425.presentacion

import iesra.prog2425.modelo.Actividad
import iesra.prog2425.servicios.ActividadService
import iesra.prog2425.servicios.UsuarioService

interface Interfaz {
    val actividadService: ActividadService
    val usuarioService: UsuarioService

    fun mostrarMenu()
    fun manejarOpcion(opcion: Int)
    fun leerString(): String
    fun leerNum(): Int
    fun mostrar(x: Any)
    fun mostrarActividades(x: List<Actividad>)
    fun mostrarInput(x: Any)
    fun listarActividades()
}