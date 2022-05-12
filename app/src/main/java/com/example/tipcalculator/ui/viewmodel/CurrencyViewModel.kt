package com.example.tipcalculator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    val readAllData: Flow<Currency> = repository.readAllData

    private val _result: MutableSharedFlow<String> = MutableSharedFlow()
    val result: SharedFlow<String> get() = _result

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

    suspend fun calculateTip(number: String, spinnerResult: String) {
        var mathResult: BigDecimal? = null
        var symbol = ""
        var usd: BigDecimal
        var eur: BigDecimal
        Log.d("QQQ", "Starting collecting")
        readAllData.collect {
            if (it != null) {
                Log.d("QQQ", "Collecting in ViewModel")
                usd = (it.usd ?: "0").toBigDecimal()
                eur = (it.eur ?: "0").toBigDecimal()

                when (spinnerResult) {
                    "RUB-USD" -> mathResult = number.toBigDecimal() / usd
                    "RUB-EUR" -> mathResult = number.toBigDecimal() / eur
                    "EUR-RUB" -> mathResult = number.toBigDecimal() * eur
                    "USD-RUB" -> mathResult = number.toBigDecimal() * usd
                }
                symbol = when (spinnerResult) {
                    "RUB-EUR" -> "€"
                    "RUB-USD" -> "$"
                    else -> "₽"
                }
                _result.emit(String.format("%.2f", mathResult) + symbol)
                Log.d("QQQ", "Emited: ${String.format("%.2f", mathResult) + symbol}")
            }
        }

    }
}