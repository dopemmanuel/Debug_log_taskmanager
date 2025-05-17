package servicios

import es.prog2425.taskmanager.datos.UsuarioRepository
import es.prog2425.taskmanager.modelo.Usuario
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify



class UsuarioServiceTest : DescribeSpec({
    val mockUsuarioRepo = mockk<UsuarioRepository>()
    val servicio = UsuarioService(mockUsuarioRepo)

    val usuarioTest = Usuario(1, "Ana", "ana@test.com")

    describe("Pruebas para UsuarioService") {
        describe("registrarUsuario()") {
            it("debería registrar usuario con datos válidos") {
                every { mockUsuarioRepo.crearUsuario("Ana", "ana@test.com") } returns usuarioTest

                val resultado = servicio.registrarUsuario("Ana", "ana@test.com")

                resultado shouldBe usuarioTest
                verify { mockUsuarioRepo.crearUsuario("Ana", "ana@test.com") }
            }

            it("debería lanzar excepción con email inválido") {
                shouldThrow<IllegalArgumentException> {
                    servicio.registrarUsuario("Ana", "emailmalo")
                }.message shouldBe "Email no válido"
            }
        }

        describe("listarUsuarios()") {
            it("debería retornar lista de usuarios cuando hay registros") {
                every { mockUsuarioRepo.obtenerTodos() } returns listOf(usuarioTest)

                val resultado = servicio.listarUsuarios()

                resultado.size shouldBe 1
                resultado[0] shouldBe usuarioTest
            }

            it("debería retornar lista vacía cuando no hay usuarios") {
                every { mockUsuarioRepo.obtenerTodos() } returns emptyList()

                servicio.listarUsuarios() shouldBe emptyList()
            }
        }

        describe("obtenerUsuario()") {
            it("debería retornar usuario cuando ID existe") {
                every { mockUsuarioRepo.buscarPorId(1) } returns usuarioTest

                servicio.obtenerUsuario(1) shouldBe usuarioTest
            }

            it("debería retornar null cuando ID no existe") {
                every { mockUsuarioRepo.buscarPorId(99) } returns null

                servicio.obtenerUsuario(99) shouldBe null
            }
        }
    }
})