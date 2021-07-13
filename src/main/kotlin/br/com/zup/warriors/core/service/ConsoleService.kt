package br.com.zup.warriors.core.service

import br.com.zup.warriors.core.mapper.ConsoleConverter
import br.com.zup.warriors.core.model.Console
import br.com.zup.warriors.core.ports.ConsoleRepositoryPort
import br.com.zup.warriors.core.ports.ConsoleServicePort
import br.com.zup.warriors.database.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.infrastructure.dto.ConsoleBroker
import java.util.*
import javax.inject.Singleton

@Singleton
class ConsoleService(private var repository: ConsoleRepositoryPort) : ConsoleServicePort {

    override fun atualizaConsole(id: UUID, novosDados: Console): ConsoleBroker {
        val console = repository.findById(id).also {
            if (it != null) {
                if (novosDados.nome != null && novosDados.nome != "") {
                    it.nome = novosDados.nome
                }
                if (novosDados.marca != null && novosDados.marca != "") {
                    it.marca = novosDados.marca
                }
                if (novosDados.dataLancamento != null) {
                    it.dataLancamento = novosDados.dataLancamento
                }
            }
        }
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")
        return ConsoleConverter.consoleEntityToConsoleBroker(repository.update(console))
    }

    override fun cadastraConsole(console: Console) {
        repository.save(ConsoleConverter.consoleToConsoleEntity(console))
    }

    override fun deletaConsole(id: UUID) {
        repository.delete(id)
    }

}