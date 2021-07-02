package br.com.zup.warriors.infrastructure.dto

data class EventsInformationDto(
    var event: String = "",
    val consoleBroker: ConsoleBroker
)