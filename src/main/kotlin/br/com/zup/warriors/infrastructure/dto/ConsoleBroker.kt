package br.com.zup.warriors.infrastructure.dto

import java.time.LocalDate

data class ConsoleBroker(
    val nome: String = "",
    val marca: String = "",
    val dataLancamento: LocalDate? = null,
    val id: String? = ""
)