package com.commandiron.currencyconverter_app_by_command.repo

import com.commandiron.currencyconverter_app_by_command.model.ResponseFromApi
import com.commandiron.currencyconverter_app_by_command.service.ConverterAPI
import com.commandiron.currencyconverter_app_by_command.util.Constants
import com.commandiron.currencyconverter_app_by_command.util.Resource
import javax.inject.Inject

class ConverterRepositoryImpl @Inject constructor(private val api: ConverterAPI): ConverterRepository {
    override suspend fun getConvertResult(base_currency: String): Resource<ResponseFromApi> {

        Resource.Loading(null)

        val response = try {
            api.getConvertResult(Constants.API_KEY,base_currency)
        }catch (e: Exception){
            return Resource.Error("Error")
        }

        return Resource.Success(response)
    }
}