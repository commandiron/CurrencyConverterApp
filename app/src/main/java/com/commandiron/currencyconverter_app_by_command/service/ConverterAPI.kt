package com.commandiron.currencyconverter_app_by_command.service

import com.commandiron.currencyconverter_app_by_command.model.ResponseFromApi
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterAPI {

    //https://free.currconv.com/api/v7/convert?q=USD_TRY&compact=ultra&apiKey=50b1ee5c9f1c32a2e86d

    //https://freecurrencyapi.net/api/v2/latest?apikey=0795ad40-2cf0-11ec-aad3-3db234104282&base_currency=TRY

    @GET("latest")
    suspend fun getConvertResult(
        @Query("apikey") apikey: String,
        @Query("base_currency") base_currency: String,
    ): ResponseFromApi

}