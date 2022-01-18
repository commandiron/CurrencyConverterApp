package com.commandiron.currencyconverter_app_by_command.model

data class Query(
    val apikey: String,
    val base_currency: String,
    val timestamp: Int
)