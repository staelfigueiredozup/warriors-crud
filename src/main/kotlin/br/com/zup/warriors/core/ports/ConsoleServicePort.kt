package br.com.zup.warriors.core.ports

import br.com.zup.warriors.core.model.Console
import java.util.*

interface ConsoleServicePort {
    fun atualizaConsole(id: UUID, novosDados: Console): Console
    fun cadastraConsole(console: Console)
    fun deletaConsole(id: UUID)
}