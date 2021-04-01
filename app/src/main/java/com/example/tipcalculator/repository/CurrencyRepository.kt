package com.example.tipcalculator.repository

import androidx.lifecycle.LiveData
import com.example.tipcalculator.data.CurrencyDao
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.other.Constants.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyDao: CurrencyDao
) {

    val readAllData: LiveData<Currency> = currencyDao.readAllData()

    private suspend fun addCurrency(currency: Currency) {
        currencyDao.addCurrency(currency)
    }

    suspend fun downloadCurrency() = withContext(Dispatchers.IO) {
            val document = Jsoup.connect(URL).get()
            val currency = document
                .getElementsByClass("standard-table standard-table--row-highlight")
                .select("tr td")

            val usd = currency[3].text()
            val eur = currency[8].text()

            withContext(Dispatchers.Main) {
                addCurrency(Currency(null, usd, eur))
            }
    }

    suspend fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }
}