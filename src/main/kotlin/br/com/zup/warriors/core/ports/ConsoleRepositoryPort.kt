package br.com.zup.warriors.core.ports

import br.com.zup.warriors.core.model.Console
import br.com.zup.warriors.database.entity.ConsoleEntity
import java.util.*
import javax.inject.Singleton

@Singleton
interface ConsoleRepositoryPort {

    fun save (consoleEntity: ConsoleEntity) : ConsoleEntity
    fun findById (id: UUID) : ConsoleEntity?
    fun update (consoleEntity: ConsoleEntity): ConsoleEntity
    fun delete(id: UUID)

}