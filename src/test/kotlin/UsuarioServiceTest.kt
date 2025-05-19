package es.prog2425.taskmanager.servicios


import es.prog2425.taskmanager.datos.UsuarioRepository
import es.prog2425.taskmanager.modelo.Usuario
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UsuarioServiceTest : DescribeSpec({

    val repo = mockk<UsuarioRepository>()
    val service = UsuarioService(repo)

    describe("UsuarioService") {

        context("registrarUsuario(nombre, email)") {

            it("Caso nominal: Usuario creado correctamente") {
                val usuarioEsperado = Usuario(1, "Ana", "ana@correo.com")
                every { repo.crearUsuario("Ana", "ana@correo.com") } returns usuarioEsperado

                val resultado = service.registrarUsuario("Ana", "ana@correo.com")

                resultado shouldBe usuarioEsperado
                verify(exactly = 1) { repo.crearUsuario("Ana", "ana@correo.com") }
            }

            it("Caso borde o de error: Error") {
                every { repo.crearUsuario(any(), any()) } throws RuntimeException("Error")

                shouldThrow<RuntimeException> {
                    service.registrarUsuario("X", "x@x.com")
                }
                verify(exactly = 1) { repo.crearUsuario("X", "x@x.com") }
            }
        }

        context("listarUsuarios()") {

            it("Caso nominal: Devuelve lista de usuarios") {
                val lista = listOf(
                    Usuario(1, "A", "a@a.com"),
                    Usuario(2, "B", "b@b.com")
                )
                every { repo.obtenerTodos() } returns lista

                // Llamada al servicio
                val resultado = service.listarUsuarios()

                // Comprobaciones
                resultado shouldBe lista
                verify(exactly = 1) { repo.obtenerTodos() }
            }

            it("Caso borde o de error: Devuelve lista vacia") {
                every { repo.obtenerTodos() } returns emptyList()

                val resultado = service.listarUsuarios()

                resultado shouldBe emptyList()
                verify(exactly = 1) { repo.obtenerTodos() }
            }
        }

        context("obtenerUsuario(id)") {

            it("Caso nominal: retorna un Usuario") {
                val usuario = Usuario(5, "C", "c@c.com")
                every { repo.buscarPorId(5) } returns usuario

                val resultado = service.obtenerUsuario(5)

                resultado shouldBe usuario
                verify(exactly = 1) { repo.buscarPorId(5) }
            }

            it("Caso borde o de error: Retorna un nulo para los Id que no existen") {
                every { repo.buscarPorId(999) } returns null

                val resultado = service.obtenerUsuario(999)

                resultado shouldBe null
                verify(exactly = 1) { repo.buscarPorId(999) }
            }
        }
    }
})