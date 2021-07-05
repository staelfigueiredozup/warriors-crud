package br.com.zup.warriors.core.service

import br.com.zup.warriors.core.model.Console
import br.com.zup.warriors.core.ports.ConsoleRepositoryPort
import br.com.zup.warriors.database.entity.ConsoleEntity
import br.com.zup.warriors.database.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.database.repository.ConsoleRepositoryImpl
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsoleServiceTest : AnnotationSpec() {

    private val repository = mockk<ConsoleRepositoryImpl>(relaxed = true)
    private val service = ConsoleService(repository)

    lateinit var consoleEntity : ConsoleEntity
    lateinit var novosDados: Console
    lateinit var console: Console

    companion object {
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp(){
        consoleEntity = ConsoleEntity(nome = "nome", marca = "marca", dataLancamento = LocalDate.now(), id, dataCadastro = LocalDate.now())
        novosDados = Console(nome = "nome", marca = "marca", dataLancamento = LocalDate.now(), id, dataCadastro = LocalDate.now())
        console = Console(nome = "nome", marca = "marca", dataLancamento = LocalDate.now())
    }

    @Test
    fun `deve atualizar um console`(){
        //cenário
        every { repository.findById(id) } answers { consoleEntity }
        every { repository.update(consoleEntity) } answers { consoleEntity }

        //ação
        val result = service.atualizaConsole(id, novosDados)

        //validação
        result shouldBe novosDados
    }

    @Test
    fun `deve lancar um erro quando tenta atualizar um console inexistente no banco de dados`(){
        //cenário
        every { repository.findById(id) } answers { null }

        //ação
        val result = shouldThrow<ConsoleNaoEncontradoException> {
            service.atualizaConsole(id, novosDados)
        }

        //validação
        result.message shouldBe "Console inexistente no banco de dados"
    }

    @Test
    fun `deve cadastrar um console`(){
        //cenário
        every { repository.save( any() ) } answers { consoleEntity }

        //ação
        val result = service.cadastraConsole(console)

        //validação
        result shouldBe Unit
    }

    @Test
    fun `deve deletar um console pelo id`(){
        //cenário
        every { repository.delete(id) } answers { Unit }

        //ação
        val result: Unit = service.deletaConsole(id)

        //validação
        result shouldBe Unit
    }

}