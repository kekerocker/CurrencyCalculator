package com.example.tipcalculator.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.math.BigDecimal

class CurrencyViewModel @ViewModelInject constructor(
    val repository: CurrencyRepository
) : ViewModel() {

    val readAllData: LiveData<Currency> = repository.readAllData

    val symbolLiveData = MutableLiveData<String>()
    val resultLiveData = MutableLiveData<String>()

    init {
        downloadCurrency()
    }

    private fun downloadCurrency() {
        viewModelScope.launch {
            repository.downloadCurrency()
        }
    }

    fun deleteAllCurrencies() {
        viewModelScope.launch {
            repository.deleteAllCurrencies()
        }
    }

    fun calculateTip(number: String, spinnerResult: String) {
        var mathResult: BigDecimal? = null

        val usd = readAllData.value?.usd?.toBigDecimal()
        val eur = readAllData.value?.eur?.toBigDecimal()

        when (spinnerResult) {
            "RUB-USD" -> mathResult = number.toBigDecimal() / usd!!
            "RUB-EUR" -> mathResult = number.toBigDecimal() / eur!!
            "EUR-RUB" -> mathResult = number.toBigDecimal() * eur!!
            "USD-RUB" -> mathResult = number.toBigDecimal() * usd!!
        }
        symbolLiveData.value = when (spinnerResult) {
            "RUB-EUR" ->  "€"
            "RUB-USD" ->  "$"
            else -> "₽"
        }
       resultLiveData.value = String.format("%.2f", mathResult)
    }
}