package br.com.zup.warriors.database.repository

import br.com.zup.warriors.database.entity.ConsoleEntity
import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.datastax.oss.driver.api.core.cql.SimpleStatement
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

@MicronautTest
class ConsoleRepositoryImplTest : AnnotationSpec(){

    val cqlSession = mockk<CqlSession>(relaxed = true)
    val repository = ConsoleRepositoryImpl(cqlSession)
    val row = mockk<Row>(relaxed = true)

    lateinit var consoleEntity: ConsoleEntity

    companion object{
        val id = UUID.randomUUID()
    }

    @BeforeEach
    fun setUp(){
        consoleEntity = ConsoleEntity(nome = "consoleA", marca = "consoleA", dataLancamento = LocalDate.now(), dataCadastro = LocalDate.now(), id = id)
    }

    @Test
    fun `deve salvar um consoleEntity`(){
        //cenário
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

        //ação
        val result = repository.save(consoleEntity)
        println(result)

        //validação
        result shouldBe consoleEntity
        result.id shouldBe consoleEntity.id
    }

    @Test
    fun `deve encontrar um consoleEntity pelo ID`(){
        //cenário
        every { cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console WHERE id = ?")
                .addPositionalValue(id)
                .build()
        ).one()  } answers { row }

        every { row.getString("nome") } answers { consoleEntity.nome }
        every { row.getString("marca") } answers { consoleEntity.marca }
        every { row.getLocalDate("data_lancamento") } answers { consoleEntity.dataLancamento }
        every { row.getLocalDate("data_cadastro") } answers { consoleEntity.dataCadastro }
        every { row.getUuid("id") } answers { consoleEntity.id }

        //ação
        val result = repository.findById(id)

        //validação
        result shouldBe consoleEntity

    }

    @Test
    fun `deve atualizar um console`(){
        //cenário
        cqlSession.execute(
            SimpleStatement
                .builder("UPDATE console.console SET nome = ?, marca = ?, data_lancamento = ? WHERE id = ?")
                .addPositionalValue(consoleEntity.nome)
                .addPositionalValue(consoleEntity.marca)
                .addPositionalValue(consoleEntity.dataLancamento)
                .addPositionalValue(consoleEntity.id)
                .build()
        )
        every { cqlSession.execute(SimpleStatement
            .builder("SELECT * FROM console.console WHERE id = ?")
            .addPositionalValue(consoleEntity.id)
            .build()
        ).one() } returns row

        every { row?.getString("nome") } answers { consoleEntity.nome }
        every { row?.getString("marca") } answers { consoleEntity.marca }
        every { row?.getLocalDate("data_lancamento") } answers { consoleEntity.dataLancamento }
        every { row?.getLocalDate("data_cadastro") } answers { consoleEntity.dataCadastro }
        every { row?.getUuid("id") } answers { consoleEntity.id }

        //ação
        val result = repository.update(consoleEntity)

        //validação
        result shouldBe consoleEntity
    }

    @Test
    fun `deve deletar um console`(){
        //cenário
        cqlSession.execute(
            SimpleStatement
                .builder("DELETE FROM console.console WHERE id = ?")
                .addPositionalValue(id)
                .build()
        )

        //ação
        val result = repository.delete(id)

        //validação
        result shouldBe Unit
    }

    @Test
    fun `deve retornar null quando o id não for encontrado`() {
        //cenário
        every { cqlSession.execute(
            SimpleStatement
                .builder("SELECT * FROM console.console WHERE id = ?")
                .addPositionalValue(id)
                .build()
        ).one() } answers { null }

        //ação
        val result = repository.findById(id)

        //validação
        result shouldBe null
    }

}