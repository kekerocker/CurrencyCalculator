package com.example.tipcalculator.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    val readAllData: LiveData<Currency> = repository.readAllData

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
        val usd = readAllData.value?.usd?.toBigDecimal() ?: "0".toBigDecimal()
        val eur = readAllData.value?.eur?.toBigDecimal() ?: "0".toBigDecimal()

        when (spinnerResult) {
            "RUB-USD" -> mathResult = number.toBigDecimal() / usd
            "RUB-EUR" -> mathResult = number.toBigDecimal() / eur
            "EUR-RUB" -> mathResult = number.toBigDecimal() * eur
            "USD-RUB" -> mathResult = number.toBigDecimal() * usd
        }
        val symbol: String = when (spinnerResult) {
            "RUB-EUR" -> "€"
            "RUB-USD" -> "$"
            else -> "₽"
        }
       resultLiveData.value = String.format("%.2f", mathResult) + symbol
    }
}