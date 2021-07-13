package br.com.zup.warriors.core.ports

import br.com.zup.warriors.core.model.Console
import br.com.zup.warriors.infrastructure.dto.ConsoleBroker
import java.util.*

interface ConsoleServicePort {
    fun atualizaConsole(id: UUID, novosDados: Console): ConsoleBroker
    fun cadastraConsole(console: Console)
    fun deletaConsole(id: UUID)
}