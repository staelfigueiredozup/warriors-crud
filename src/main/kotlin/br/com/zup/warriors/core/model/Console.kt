package br.com.zup.warriors.core.model

import java.time.LocalDate
import java.util.*

data class Console(
    var nome: String = "",
    var marca: String = "",
    var dataLancamento: LocalDate?,

    var id: UUID? = null,
    var dataCadastro: LocalDate = LocalDate.now()
)