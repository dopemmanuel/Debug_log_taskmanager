# Anotaciones sobre el debugging en el codigo:

**Error #1: Constructor de Tarea Inconsistente**
***

````kotlin
class Tarea(
    id: Int,
    descripcion: String,
    var estado: Estado = Estado.ABIERTA,
    private val subtareas: MutableList<Tarea> = mutableListOf(),
    var usuarioAsignado: Usuario? = null
) : Actividad(id.toString(), descripcion) {}
````
- La clase Actividad espera (descripcion: String, descripcion1: String), 
pero Tarea intenta pasar id.toString() como primer parámetro. Además, hay un error tipográfico en Evento (se usa Evento en el companion object pero Evento en el constructor).

**La solucion:**

- Aplicar el codigo que diego nos ha brindado pero agregándole algunos cambios.
- O hacer la clase abstracta

````kotlin
class Tarea private constructor(descripcion: String): Actividad(descripcion) {
    private var estado: Estado = Estado.ABIERTA
}
````
**Error #2: El Método crearTarea() en ActividadService**

***

````kotlin
fun crearTarea(descripcion: String): Tarea {
    val tarea = Tarea(descripcion)
    actividadRepo.agregarTarea(tarea)
    return tarea
}
````
- El metodo crearInstancia() no existe

**La solucion:**
- Agregar el metodo o remplazarlo con la siguiente modificion.

```kotlin
fun crearTarea(descripcion: String): Tarea {
    val tarea = Tarea(descripcion)
    actividadRepo.agregarTarea(tarea)
    return tarea
}
```