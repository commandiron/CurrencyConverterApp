package com.commandiron.currencyconverter_app_by_command.model

data class DataState(
    val isLoading : Boolean = false,
    val data : Map<String, Double>? = null,
    val error : String = "Hata Mesajı Yok")