package br.com.zup.warriors.dto

import java.time.LocalDate

data class EventsInformation(
    var event: String = "",
    val nome: String = "",
    val marca: String = "",
    val id: String = "",
    val dataLancamento: LocalDate? = null
) {

    fun toConsoleRequest(): ConsoleRequest{
        return ConsoleRequest(
            nome = nome,
            marca = marca,
            dataLancamento = dataLancamento
        )
    }

    fun toDadosRequest(): DadosRequest {
        return DadosRequest(
            nome = nome,
            marca = marca,
            dataLancamento = dataLancamento
        )
    }
}