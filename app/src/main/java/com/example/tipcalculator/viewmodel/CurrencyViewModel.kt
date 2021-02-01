package com.example.tipcalculator.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tipcalculator.data.CurrencyDatabase
import com.example.tipcalculator.internet.DownloadCurrencies
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    var resultTest: MutableLiveData<String>
    private var repository: CurrencyRepository
    var getCurrencies: LiveData<Currency>

    init {
        val currencyDao = CurrencyDatabase.getDatabase(application).currencyDao()
        val downloadData = DownloadCurrencies(this)
        repository = CurrencyRepository(currencyDao, downloadData)
        getCurrencies = repository.getCurrencies
        resultTest = MutableLiveData()

        downloadCurrency()
    }

    fun addCurrency(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCurrency(currency)
        }
    }

    fun deleteAllCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllCurrencies()
        }
    }

    private fun downloadCurrency() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.downloadCurrencies()
        }
    }
}