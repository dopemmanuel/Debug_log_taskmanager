package iesra.prog2425.modelo

data class Usuario(
    val id: Int,
    val nombre: String,
    val email: String
) {
    init {
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.contains("@")) { "Email no válido" }
    }
}