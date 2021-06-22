package br.com.zup.warriors.service

import br.com.zup.warriors.dto.ConsoleRequest
import br.com.zup.warriors.dto.ConsoleResponse
import br.com.zup.warriors.dto.DadosRequest
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.model.Console
import br.com.zup.warriors.repository.ConsoleRepository
import br.com.zup.warriors.repository.ConsoleRepositoryImpl
import br.com.zup.warriors.utils.ConsoleUtils
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
internal class ConsoleServiceImplTest: AnnotationSpec() {

    private val repository = mockk<ConsoleRepositoryImpl>()
    private val service = ConsoleServiceImpl(repository)
    private val consoleUtils = mockk<ConsoleUtils.Companion>()

    private lateinit var consoleResponse: ConsoleResponse
    private lateinit var dadosRequest: DadosRequest
    private lateinit var console: Console
    private lateinit var consoleCompleto: Console
    private lateinit var consoleRequest: ConsoleRequest

    companion object {
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp(){
        consoleResponse = ConsoleResponse(id, "nintendo", "nintendo", dataLancamento = null, dataCadastro = LocalDate.now().toString())
        dadosRequest = DadosRequest(nome = "nintendo", marca = "nintendo")
        console = Console(nome = "nintendo", marca = "nintendo", dataLancamento = null, dataCadastro = LocalDate.now())
        consoleCompleto = Console(nome = "nintendo", marca = "nintendo", dataLancamento = null, dataCadastro = LocalDate.now(), id = id)
        consoleRequest = ConsoleRequest(nome = "nintendo", marca = "nintendo", dataLancamento = null)
    }

    @Test
    fun `deve atualizar um console`(){
        //cenário
        every { service.atualizaConsole(id, dadosRequest) } answers { consoleResponse }
        every { repository.findById(id) } answers { console }
        every { consoleUtils.atualiza(console, dadosRequest) } answers { consoleCompleto }
        every { repository.update(console) } answers { consoleCompleto }

        //ação
        val result = service.atualizaConsole(id, dadosRequest)

        //validação
        result.dataCadastro shouldBe LocalDate.now().toString()
        result.id shouldBe id
        result.nome shouldBe "nintendo"
        result.marca shouldBe "nintendo"
    }

    @Test
    fun `deve lançar um erro quando tenta atualizar um console inexistente no banco de dados`(){
        //cenário
        every { repository.findById(id) } answers { null }

        //ação
        val result = shouldThrow<ConsoleNaoEncontradoException> {
            service.atualizaConsole(id, dadosRequest)
        }

        //validação
        result.message shouldBe "Console inexistente no banco de dados"
    }

    @Test
    fun `deve cadastrar um console`(){
        //cenário
        every { service.cadastraConsole(consoleRequest) } answers { consoleResponse }
        every { repository.save( any() ) } answers { consoleCompleto }

        //ação
        val result = service.cadastraConsole(consoleRequest)

        //validação
        result.nome shouldBe "nintendo"
    }

    @Test
    fun `deve buscar um console pelo id`(){
        //cenário
        every { service.consultaConsole(id) } answers { consoleResponse }
        every { repository.findById(id) } answers { consoleCompleto }

        //ação
        val result = service.consultaConsole(id)

        //validação
        result.nome shouldBe consoleResponse.nome
        result.marca shouldBe consoleResponse.marca
        result.dataLancamento shouldBe consoleResponse.dataLancamento.toString()
    }

    @Test
    fun `deve lançar um erro quando tenta consultar um console inexistente no banco de dados`(){
        //cenário
        every { repository.findById(id) } answers { null }

        //ação
        val result = shouldThrow<ConsoleNaoEncontradoException> {
            service.consultaConsole(id)
        }

        //validação
        result.message shouldBe "Console inexistente no banco de dados"
    }

    @Test
    fun `deve listar os consoles cadastrados`(){
        //cenário
        val console1 = ConsoleResponse(UUID.randomUUID(),"nome1", "marca1", null, LocalDate.now().toString())
        val console2 = ConsoleResponse(UUID.randomUUID(),"nome2", "marca2", null, LocalDate.now().toString())
        val console3 = ConsoleResponse(UUID.randomUUID(),"nome3", "marca3", null, LocalDate.now().toString())
        val listaConsoles = listOf(console1, console2, console3)
        every { service.listaConsoles() } answers { listaConsoles }
        every { repository.findAll() } answers { listaConsoles.map { cr ->
            Console(cr.nome, cr.marca, null, cr.id) } }

        //ação
        val result = service.listaConsoles()

        //validação
        result.size shouldBe 3
        result.get(0).nome shouldBe "nome1"
        result.get(1).nome shouldBe "nome2"
        result.get(2).nome shouldBe "nome3"
    }

    @Test
    fun `deve deletar um console pelo id`(){
        //cenário
        every { service.deletaConsole(id) } answers { Unit }
        every { repository.findById(id) } answers { consoleCompleto }
        every { repository.delete(consoleCompleto) } answers { Unit }

        //ação
        val result: Unit = service.deletaConsole(id)

        //validação
        result shouldBe Unit

    }

}