package br.com.zup.warriors.infrastructure.subscribe

import br.com.zup.warriors.core.mapper.ConsoleConverter
import br.com.zup.warriors.core.ports.ConsoleServicePort
import br.com.zup.warriors.infrastructure.dto.EventsInformationDto
import io.micronaut.nats.annotation.NatsListener
import io.micronaut.nats.annotation.Subject
import org.slf4j.LoggerFactory
import java.util.*

@NatsListener
class ConsoleServer(private val consoleService: ConsoleServicePort) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Subject("cadastro-console")
    fun recive(eventsInformation: EventsInformationDto) {
        logger.info("Mensagem do broker recebida com sucesso", eventsInformation.event)
        if(eventsInformation.event.equals("CADASTRA_CONSOLE")){

            logger.info("Cadastrando console")
            consoleService.cadastraConsole(
                ConsoleConverter.consoleBrokerToConsole(eventsInformation.consoleBroker))

        } else if (eventsInformation.event.equals("DELETA_CONSOLE")){

            logger.info("Deletando console")
            consoleService.deletaConsole(
                UUID.fromString(eventsInformation.consoleBroker.id))

        } else {

            logger.info("Atualizando console")
            consoleService.atualizaConsole(
                UUID.fromString(eventsInformation.consoleBroker.id),
                ConsoleConverter.consoleBrokerToConsole(eventsInformation.consoleBroker))

        }
    }

}