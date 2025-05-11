package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.presentacion.Consola
import es.prog2425.taskmanager.presentacion.Interfaz
import es.prog2425.taskmanager.utils.Utilidades

class GestorActividades {
    private val salida: Interfaz = Consola()
    private val servicio = ActividadService()

    // Muestra el menu principal
    fun menu() {
        var salir = false
        do {
            salida.mostrarMenu()
            when(salida.leerNum()) {
                // Opcion invalida
                -1 -> salida.mostrar("\nOpcion no valida.")
                // Crear evento
                1 -> servicio.crearEvento(pedirDescripcion(), pedirFecha(), pedirUbicacion())
                // Crear tarea
                2 -> servicio.crearTarea(pedirDescripcion())
                // Listar actividades
                3 -> salida.mostrarActividades(servicio.listarActividades())
                // Salir
                4 -> salir = true
            }
        } while(!salir)
    }

    /*
    * Pide la descripcion de la actividad a crear
    *
    * @return Un String con la descripcion de la actividad
     */
    private fun pedirDescripcion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la descripcion")
            salida.mostrarInput("> ")
            val descripcion = salida.leerString()

            if (descripcion != "") return descripcion else salida.mostrar("\nLa descripcion debe contener algo.")
        }
    }

    /*
    * Pide la fecha de la actividad a crear
    *
    * @return Un String con la fecha de la actividad
     */
    private fun pedirFecha(): String {
        while (true) {
            salida.mostrar("\nIntroduce la fecha con el siguiente formato (dd-MM-yyyy)")
            salida.mostrarInput("> ")
            val fecha = salida.leerString()

            if (Utilidades().esFechaValida(fecha)) {
                return fecha
            } else salida.mostrar("\nFecha invalida.")
        }
    }

    /*
    * Pide la ubicacion de la actividad a crear
    *
    * @return Un String con la ubicacion de la actividad
     */
    private fun pedirUbicacion(): String {
        while (true) {
            salida.mostrar("\nIntroduce la ubicacion")
            salida.mostrarInput("> ")
            val ubicacion = salida.leerString()

            if (ubicacion != "") return ubicacion else salida.mostrar("\nLa ubicacion debe contener algo.")
        }
    }
}