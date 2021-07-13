package br.com.zup.warriors.core.mapper

import br.com.zup.warriors.core.model.Console
import br.com.zup.warriors.database.entity.ConsoleEntity
import br.com.zup.warriors.infrastructure.dto.ConsoleBroker

class ConsoleConverter {

    companion object {

        fun consoleBrokerToConsole(consoleDto: ConsoleBroker) =
            Console(consoleDto.nome, consoleDto.marca, consoleDto.dataLancamento)

        fun consoleToConsoleEntity(console: Console) =
            ConsoleEntity(console.nome, console.marca, console.dataLancamento, console.id, console.dataCadastro)

        fun consoleEntityToConsole(console: ConsoleEntity) =
            Console(console.nome, console.marca, console.dataLancamento, console.id, console.dataCadastro)

        fun consoleEntityToConsoleBroker(consoleEntity: ConsoleEntity) =
            ConsoleBroker(consoleEntity.nome, consoleEntity.marca, consoleEntity.dataLancamento, consoleEntity.id.toString())

    }


}