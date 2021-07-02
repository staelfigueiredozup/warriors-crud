//package br.com.zup.warriors.service
//
//import br.com.zup.warriors.infrastructure.dto.ConsoleRequest
//import br.com.zup.warriors.infrastructure.dto.ConsoleResponse
//import br.com.zup.warriors.infrastructure.dto.DadosRequest
//import br.com.zup.warriors.database.exception.ConsoleNaoEncontradoException
//import br.com.zup.warriors.core.model.Console
//import br.com.zup.warriors.core.service.ConsoleService
//import br.com.zup.warriors.database.repository.ConsoleRepositoryImpl
//import br.com.zup.warriors.utils.ConsoleUtils
//import io.kotest.assertions.throwables.shouldThrow
//import io.kotest.core.spec.style.AnnotationSpec
//import io.kotest.matchers.shouldBe
//import io.micronaut.test.extensions.kotest.annotation.MicronautTest
//import io.mockk.every
//import io.mockk.mockk
//import java.time.LocalDate
//import java.util.*
//
//@MicronautTest
//internal class ConsoleServiceImplTest: AnnotationSpec() {
//
//    private val repository = mockk<ConsoleRepositoryImpl>()
//    private val service = ConsoleService(repository)
//    private val consoleUtils = mockk<ConsoleUtils.Companion>()
//
//    private lateinit var consoleResponse: ConsoleResponse
//    private lateinit var dadosRequest: DadosRequest
//    private lateinit var console: Console
//    private lateinit var consoleCompleto: Console
//    private lateinit var consoleRequest: ConsoleRequest
//
//    companion object {
//        val id = UUID.randomUUID()
//    }
//
//    @BeforeEach
//    fun setUp(){
//        consoleResponse = ConsoleResponse(id, "nintendo", "nintendo", dataLancamento = null, dataCadastro = LocalDate.now().toString())
//        dadosRequest = DadosRequest(nome = "nintendo", marca = "nintendo")
//        console = Console(nome = "nintendo", marca = "nintendo", dataLancamento = null, dataCadastro = LocalDate.now())
//        consoleCompleto = Console(nome = "nintendo", marca = "nintendo", dataLancamento = null, dataCadastro = LocalDate.now(), id = id)
//        consoleRequest = ConsoleRequest(nome = "nintendo", marca = "nintendo", dataLancamento = null)
//    }
//
//    @Test
//    fun `deve atualizar um console`(){
//        //cenário
//        every { repository.findById(id) } answers { console }
//        every { consoleUtils.atualiza(console, dadosRequest) } answers { consoleCompleto }
//        every { repository.update(console) } answers { consoleCompleto }
//
//        //ação
//        val result = service.atualizaConsole(id, dadosRequest)
//
//        //validação
//        result.dataCadastro shouldBe LocalDate.now().toString()
//        result.id shouldBe id
//        result.nome shouldBe "nintendo"
//        result.marca shouldBe "nintendo"
//    }
//
//    @Test
//    fun `deve lancar um erro quando tenta atualizar um console inexistente no banco de dados`(){
//        //cenário
//        every { repository.findById(id) } answers { null }
//
//        //ação
//        val result = shouldThrow<ConsoleNaoEncontradoException> {
//            service.atualizaConsole(id, dadosRequest)
//        }
//
//        //validação
//        result.message shouldBe "Console inexistente no banco de dados"
//    }
//
//    @Test
//    fun `deve cadastrar um console`(){
//        //cenário
//        every { repository.save( any() ) } answers { consoleCompleto }
//
//        //ação
//        val result = service.cadastraConsole(consoleRequest)
//
//        //validação
//        result.nome shouldBe consoleResponse.nome
//        result.marca shouldBe consoleResponse.marca
//    }
//
//    @Test
//    fun `deve deletar um console pelo id`(){
//        //cenário
//        every { repository.findById(id) } answers { consoleCompleto }
//        every { repository.delete(consoleCompleto) } answers { Unit }
//
//        //ação
//        val result: Unit = service.deletaConsole(id)
//
//        //validação
//        result shouldBe Unit
//    }
//
//}