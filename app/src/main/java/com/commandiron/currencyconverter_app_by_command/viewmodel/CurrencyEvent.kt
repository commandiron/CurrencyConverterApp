package com.commandiron.currencyconverter_app_by_command.viewmodel

sealed class CurrencyEvent {
    data class Search(val base_currency :String) : CurrencyEvent()
}
