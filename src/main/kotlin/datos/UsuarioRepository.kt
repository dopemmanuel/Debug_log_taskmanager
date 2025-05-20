package iesra.prog2425.datos

import iesra.prog2425.modelo.Usuario

class UsuarioRepository {
    private val usuarios = mutableListOf<Usuario>()
    private var contadorId = 1


    fun crearUsuario(nombre: String, email: String): Usuario {
        val usuario = Usuario(contadorId++, nombre, email)
        usuarios.add(usuario)
        return usuario
    }

    fun buscarPorId(id: Int): Usuario? = usuarios.find { it.id == id }

    fun obtenerTodos(): List<Usuario> = usuarios.toList()
}