## Métodos públicos a probar

### `registrarUsuario(nombre: String, email: String)`
- **Parámetros**: nombre y email del usuario
- **Resultado**: Retorna el usuario creado o lanza excepción si los datos son inválidos

### `listarUsuarios()`
- **Parámetros**: Ninguno
- **Resultado**: Retorna lista de usuarios (puede estar vacía)

### `obtenerUsuario(id: Int)`
- **Parámetros**: ID del usuario
- **Resultado**: Retorna el usuario o `null` si no existe

---

## Tabla de casos de prueba

| Método             | Caso de prueba   | Estado inicial del mock                                | Acción                                               | Resultado esperado                   |
|--------------------|------------------|----------------------------------------------------------|------------------------------------------------------|--------------------------------------|
| `registrarUsuario()` | Datos válidos     | `mockRepo.crearUsuario` devuelve usuario                | `servicio.registrarUsuario("Ana", "ana15@test.com")` | Retorna usuario creado               |
| `registrarUsuario()` | Email inválido    | -                                                        | `servicio.registrarUsuario("Ana", "emailmalo")`      | Lanza `IllegalArgumentException`     |
| `registrarUsuario()` | Nombre vacío      | -                                                        | `servicio.registrarUsuario("", "ana15@test.com")`    | Lanza `IllegalArgumentException`     |
| `listarUsuarios()`   | Hay usuarios      | `mockRepo.obtenerTodos` devuelve lista con usuarios     | `servicio.listarUsuarios()`                          | Retorna lista no vacía               |
| `listarUsuarios()`   | No hay usuarios   | `mockRepo.obtenerTodos` devuelve lista vacía            | `servicio.listarUsuarios()`                          | Retorna lista vacía                  |
| `obtenerUsuario()`   | ID existe         | `mockRepo.buscarPorId` devuelve usuario                 | `servicio.obtenerUsuario(1)`                         | Retorna usuario                      |
| `obtenerUsuario()`   | ID no existe      | `mockRepo.buscarPorId` devuelve `null`                  | `servicio.obtenerUsuario(99)`                        | Retorna `null`                       |
