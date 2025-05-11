package es.prog2425.taskmanager.servicios

import es.prog2425.taskmanager.datos.UsuarioRepository
import es.prog2425.taskmanager.modelo.Usuario


class UsuarioService(private val usuarioRepository: UsuarioRepository) {
    fun registrarUsuario(nombre: String, email: String): Usuario {
        return usuarioRepository.crearUsuario(nombre, email)
    }

    fun listarUsuarios(): List<Usuario> {
        return usuarioRepository.obtenerTodos()
    }

    fun obtenerUsuario(id: Int): Usuario? {
        return usuarioRepository.buscarPorId(id)
    }
}