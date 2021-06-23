package br.com.zup.warriors.listener

import br.com.zup.warriors.dto.ConsoleRequest
import br.com.zup.warriors.dto.ConsoleResponse
import br.com.zup.warriors.dto.DadosRequest
import br.com.zup.warriors.dto.EventsInformation
import br.com.zup.warriors.model.Console
import br.com.zup.warriors.repository.ConsoleRepository
import br.com.zup.warriors.service.ConsoleService
import io.kotest.assertions.show.show
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsoleServerTest : AnnotationSpec() {

    val consoleService = mockk<ConsoleService>()
    val consoleServer = ConsoleServer(consoleService)

    lateinit var eventsInformation: EventsInformation
    lateinit var consoleResponse: ConsoleResponse
    lateinit var consoleRequest: ConsoleRequest

    companion object {
        val id = UUID.randomUUID().toString()
    }

    @BeforeEach
    fun setUp() {
        consoleResponse = ConsoleResponse(
            UUID.fromString(id),
            "consoleA",
            "marcaA",
            LocalDate.now().toString(),
            LocalDate.now().toString()
        )
        consoleRequest = ConsoleRequest(
            "consoleA",
            "marcaA",
            LocalDate.now(),
        )
    }

    @Test
    fun `deve receber a mensagem para cadastro e chamar o service`(){
        //cenario
        eventsInformation = EventsInformation(
            event = "CADASTRA_CONSOLE", nome = "consoleA", marca = "marcaA", id = id,
            dataLancamento = LocalDate.now()
        )
        every { consoleService.cadastraConsole(consoleRequest) } answers { consoleResponse }

        //ação
        val result = consoleServer.recive(eventsInformation)

        //validação
        result shouldBe Unit
    }

    @Test
    fun `deve receber a mensagem para atualização e chamar o service`(){
        //cenário
        eventsInformation = EventsInformation(
            event = "ATUALIZA_CONSOLE", nome = "consoleA", marca = "marcaA", id = id,
            dataLancamento = LocalDate.now()
        )
        every { consoleService.atualizaConsole(any(), any()) } answers { consoleResponse }

        //ação
        val result = consoleServer.recive(eventsInformation)

        //validação
        result shouldBe Unit
    }

    @Test
    fun `deve receber a mensagem para deletar e chamar o service`(){
        //cenário
        eventsInformation = EventsInformation(
            event = "DELETA_CONSOLE", nome = "consoleA", marca = "marcaA", id = id,
            dataLancamento = LocalDate.now()
        )
        every { consoleService.deletaConsole(any()) } answers { Unit }

        //ação
        val result = consoleServer.recive(eventsInformation)

        //validação
        result shouldBe Unit
    }
}