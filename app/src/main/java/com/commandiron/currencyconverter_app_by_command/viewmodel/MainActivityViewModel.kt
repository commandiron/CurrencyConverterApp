package com.commandiron.currencyconverter_app_by_command.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.currencyconverter_app_by_command.repo.ConverterRepository
import com.commandiron.currencyconverter_app_by_command.util.Constants.CURRENCY_FORMAT
import com.commandiron.currencyconverter_app_by_command.util.Constants.SELECT_CURRENCY_STRING
import com.commandiron.currencyconverter_app_by_command.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: ConverterRepository
): ViewModel() {

    var allCurrencyList = mutableStateOf<List<String>>(listOf(SELECT_CURRENCY_STRING) + listOf())
    var multipledResult = mutableStateOf("1.00")

    init {
        getAllCurrencyList()
    }

    fun getAllCurrencyList(){
        viewModelScope.launch {

            var result = repository.getConvertResult("TRY")

            when(result){

                is Resource.Success -> {

                    allCurrencyList.value = listOf(SELECT_CURRENCY_STRING) + result.data!!.data.keys.toList().sortedDescending().reversed()
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        }
    }

    fun getMultipledResultForQuery(baseCurrency: String, selectedCurrencyForMap: String, multiple: Double){
        viewModelScope.launch {

            if(baseCurrency!= "" && selectedCurrencyForMap != ""){
                var result = repository.getConvertResult(baseCurrency)

                when(result){

                    is Resource.Success -> {
                        val roundResult = result.data!!.data[selectedCurrencyForMap]!!.times(multiple)
                        multipledResult.value = String.format(CURRENCY_FORMAT,roundResult)
                    }

                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                    }
                }
            }
        }
    }
}