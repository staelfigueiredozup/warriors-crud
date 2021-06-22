package br.com.zup.warriors.service

import br.com.zup.warriors.dto.ConsoleRequest
import br.com.zup.warriors.dto.ConsoleResponse
import br.com.zup.warriors.dto.DadosRequest
import br.com.zup.warriors.exception.ConsoleNaoEncontradoException
import br.com.zup.warriors.model.Console
import br.com.zup.warriors.repository.ConsoleRepository
import br.com.zup.warriors.utils.ConsoleUtils
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Singleton

@Validated
@Singleton
class ConsoleServiceImpl(private var repository: ConsoleRepository) : ConsoleService {

    override fun atualizaConsole(id: UUID, novosDados: DadosRequest): ConsoleResponse {
        val possivelConsole: Console = repository.findById(id)
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")

        val atualizado: Console = ConsoleUtils.atualiza(possivelConsole, novosDados)
        return repository.update(atualizado).toDto()
    }

    override fun cadastraConsole(request: ConsoleRequest): ConsoleResponse {
        val console = request.paraNovoConsole()
        val consoleCadastrado = repository.save(console)
        return consoleCadastrado.toDto()
    }

    override fun consultaConsole(id: UUID): ConsoleResponse {
        val possivelConsole = repository.findById(id)
            ?: throw ConsoleNaoEncontradoException("Console inexistente no banco de dados")
        return possivelConsole.toDto()
    }

    override fun listaConsoles(): List<ConsoleResponse> {
        return repository.findAll().map { console ->
            console.toDto()
        }
    }

    override fun deletaConsole(id: UUID): Unit {
        val console = repository.findById(id) ?: return
        return repository.delete(console)
    }

}