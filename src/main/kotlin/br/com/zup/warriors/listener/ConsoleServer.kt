package br.com.zup.warriors.listener

import br.com.zup.warriors.dto.EventsInformation
import br.com.zup.warriors.service.ConsoleService
import io.micronaut.nats.annotation.NatsListener
import io.micronaut.nats.annotation.Subject
import org.slf4j.LoggerFactory
import java.util.*

@NatsListener
class ConsoleServer(private val consoleService: ConsoleService) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Subject("cadastro-console")
    fun recive(eventsInformation: EventsInformation) {
        logger.info("Mensagem do broker recebida com sucesso", eventsInformation.event)
        if(eventsInformation.event.equals("CADASTRA_CONSOLE")){
            logger.info("Cadastrando console")
            consoleService.cadastraConsole(eventsInformation.toConsoleRequest())
        } else if (eventsInformation.event.equals("DELETA_CONSOLE")){
            logger.info("Deletando console")
            consoleService.deletaConsole(UUID.fromString(eventsInformation.id))
        } else {
            logger.info("Atualizando console")
            consoleService.atualizaConsole(UUID.fromString(eventsInformation.id), eventsInformation.toDadosRequest())
        }
    }

}