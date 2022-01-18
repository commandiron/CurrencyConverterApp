package com.commandiron.currencyconverter_app_by_command.model

data class ResponseFromApi(
    val `data`: Map<String, Double>,
    val query: Query
)