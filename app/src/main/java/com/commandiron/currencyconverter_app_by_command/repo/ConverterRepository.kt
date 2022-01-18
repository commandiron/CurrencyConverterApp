package com.commandiron.currencyconverter_app_by_command.repo

import com.commandiron.currencyconverter_app_by_command.model.ResponseFromApi
import com.commandiron.currencyconverter_app_by_command.service.ConverterAPI
import com.commandiron.currencyconverter_app_by_command.util.Constants
import com.commandiron.currencyconverter_app_by_command.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


interface ConverterRepository {
    suspend fun getConvertResult(base_currency: String) : Resource<ResponseFromApi>
}