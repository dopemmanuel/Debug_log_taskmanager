package es.prog2425.taskmanager.presentacion

import es.prog2425.taskmanager.modelo.Actividad
import es.prog2425.taskmanager.servicios.ActividadService
import es.prog2425.taskmanager.servicios.UsuarioService

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
}