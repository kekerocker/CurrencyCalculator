package com.example.tipcalculator.repository

import androidx.lifecycle.LiveData
import com.example.tipcalculator.data.CurrencyDao
import com.example.tipcalculator.internet.DownloadCurrencies
import com.example.tipcalculator.model.Currency

class CurrencyRepository(
    private val currencyDao: CurrencyDao,
    private val downloadCurrencies: DownloadCurrencies
) {

    val getCurrencies: LiveData<Currency> = currencyDao.getCurrencies()

    suspend fun addCurrency(currency: Currency) {
        currencyDao.addCurrency(currency)
    }

    suspend fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }

    fun downloadCurrencies() {
        downloadCurrencies.downloadData()
    }

}