package br.com.zup.warriors.database.repository

import br.com.zup.warriors.core.ports.ConsoleRepositoryPort
import br.com.zup.warriors.database.entity.ConsoleEntity
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.ResultSet
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import java.util.*
import javax.inject.Singleton

@Singleton
class ConsoleRepositoryImpl(private val cqlSession: CqlSession) : ConsoleRepositoryPort {

    override fun save(consoleEntity: ConsoleEntity): ConsoleEntity {
        consoleEntity.id = UUID.randomUUID()
        cqlSession.execute(
            SimpleStatement
                .newInstance(
                    "INSERT INTO console.console(id, nome, marca, data_lancamento, data_cadastro) VALUES (?,?,?,?,?)",
                    consoleEntity.id,
                    consoleEntity.nome,
                    consoleEntity.marca,
                    consoleEntity.dataLancamento,
                    consoleEntity.dataCadastro
                )
        )
        return consoleEntity
    }

    override fun findById(id: UUID): ConsoleEntity? {

        val row: Row? = cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console WHERE id = ?")
                .addPositionalValue(id)
                .build()
        ).one() ?: return null

        return ConsoleEntity(
            nome = row?.getString("nome")!!,
            marca = row?.getString("marca")!!,
            dataLancamento = row?.getLocalDate("data_lancamento"),
            dataCadastro = row?.getLocalDate("data_cadastro")!!,
            id = row?.getUuid("id")
        )
    }

    override fun update(consoleEntity: ConsoleEntity): ConsoleEntity {
        cqlSession.execute(
            SimpleStatement
                .builder("UPDATE console.console SET nome = ?, marca = ?, data_lancamento = ? WHERE id = ?")
                .addPositionalValue(consoleEntity.nome)
                .addPositionalValue(consoleEntity.marca)
                .addPositionalValue(consoleEntity.dataLancamento)
                .addPositionalValue(consoleEntity.id)
                .build()
        )

        var row = cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console WHERE id = ?")
                .addPositionalValue(consoleEntity.id)
                .build()
        ).one()

        return ConsoleEntity(
            nome = row?.getString("nome")!!,
            marca = row?.getString("marca")!!,
            dataLancamento = row?.getLocalDate("data_lancamento"),
            dataCadastro = row?.getLocalDate("data_cadastro")!!,
            id = row?.getUuid("id")
        )
    }

    override fun delete(id: UUID) {
        cqlSession.execute(
            SimpleStatement
                .builder("DELETE FROM console.console WHERE id = ?")
                .addPositionalValue(id)
                .build()
        )
    }

}