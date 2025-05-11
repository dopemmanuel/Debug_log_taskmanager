package es.prog2425.taskmanager.modelo
/*
Codigo anterior
enum class Estado {
    ABIERTA, CERRADA
}
**/

enum class Estado {
    ABIERTA,
    EN_PROGRESO,
    FINALIZADA;

    fun puedeTransicionarA(nuevoEstado: Estado): Boolean {
        return when (this) {
            ABIERTA -> nuevoEstado == EN_PROGRESO || nuevoEstado == FINALIZADA
            EN_PROGRESO -> nuevoEstado == FINALIZADA
            FINALIZADA -> false
        }
    }
}