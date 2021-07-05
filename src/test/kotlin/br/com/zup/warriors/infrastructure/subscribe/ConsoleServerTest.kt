package br.com.zup.warriors.infrastructure.subscribe

import br.com.zup.warriors.core.model.Console
import br.com.zup.warriors.core.ports.ConsoleServicePort
import br.com.zup.warriors.infrastructure.dto.ConsoleBroker
import br.com.zup.warriors.infrastructure.dto.EventsInformationDto
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsoleServerTest : AnnotationSpec() {

    val consoleService = mockk<ConsoleServicePort>()
    val consoleServer = ConsoleServer(consoleService)

    lateinit var eventsInformation: EventsInformationDto
    lateinit var consoleBroker: ConsoleBroker
    lateinit var console: Console
    lateinit var consoleAtualizavel: ConsoleBroker

    companion object {
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp() {
        consoleBroker = ConsoleBroker(
            nome = "consoleA",
            marca = "marcaA",
            dataLancamento = LocalDate.now()
        )
        console = Console(
            nome = "consoleA",
            marca = "marcaA",
            dataLancamento = LocalDate.now()
        )
        consoleAtualizavel = ConsoleBroker(
            nome = "consoleA",
            marca = "marcaA",
            dataLancamento = LocalDate.now(),
            id = id.toString()
        )
    }

    @Test
    fun `deve receber a mensagem para cadastro e chamar o service`(){
        //cenario
        eventsInformation = EventsInformationDto(
            event = "CADASTRA_CONSOLE", consoleBroker
        )
        every { consoleService.cadastraConsole(console) } answers { Unit }

        //ação
        val result = consoleServer.recive(eventsInformation)

        //validação
        result shouldBe Unit
    }

    @Test
    fun `deve receber a mensagem para atualização e chamar o service`(){
        //cenário
        eventsInformation = EventsInformationDto(
            event = "ATUALIZA_CONSOLE", consoleAtualizavel
        )
        every { consoleService.atualizaConsole(UUID.fromString(consoleAtualizavel.id), console) } answers { console }

        //ação
        val result = consoleServer.recive(eventsInformation)

        //validação
        result shouldBe Unit
    }

    @Test
    fun `deve receber a mensagem para deletar e chamar o service`(){
        //cenário
        consoleBroker = ConsoleBroker(
            nome = "consoleA",
            marca = "marcaA",
            dataLancamento = LocalDate.now(),
            id = id.toString()
        )
        eventsInformation = EventsInformationDto(
            event = "DELETA_CONSOLE", consoleBroker
        )
        every { consoleService.deletaConsole(id) } answers { Unit }

        //ação
        val result = consoleServer.recive(eventsInformation)

        //validação
        result shouldBe Unit
    }

}