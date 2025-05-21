package iesra.prog2425.servicios

import iesra.prog2425.datos.UsuarioRepository
import iesra.prog2425.modelo.Usuario

class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
) {
    fun registrarUsuario(
        nombre: String,
        email: String,
    ): Usuario {
        if (nombre.isBlank()) {
            throw IllegalArgumentException("El nombre no puede estar vacío")
        }

        if (!email.contains("@")) {
            throw IllegalArgumentException("Email no válido. Debe contener @")
        }
        return usuarioRepository.crearUsuario(nombre, email)
    }

    fun listarUsuarios(): List<Usuario> = usuarioRepository.obtenerTodos()

    fun obtenerUsuario(id: Int): Usuario? = usuarioRepository.buscarPorId(id)
}
