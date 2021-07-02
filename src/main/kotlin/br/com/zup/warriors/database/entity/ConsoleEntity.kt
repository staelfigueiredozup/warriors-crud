package br.com.zup.warriors.database.entity

import java.time.LocalDate
import java.util.*

data class ConsoleEntity(
    var nome: String = "",
    var marca: String = "",
    var dataLancamento: LocalDate?,

    var id: UUID? = null,
    var dataCadastro: LocalDate = LocalDate.now()
)