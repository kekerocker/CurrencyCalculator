package com.example.tipcalculator.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.tipcalculator.data.CurrencyDao
import com.example.tipcalculator.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    val getCurrencies: LiveData<Currency> = currencyDao.getCurrencies()

    private suspend fun addCurrency(currency: Currency) {
        currencyDao.addCurrency(currency)
    }

    suspend fun deleteAllCurrencies() {
        currencyDao.deleteAllCurrencies()
    }

    fun downloadCurrencies() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val document = Jsoup.connect("https://www.banki.ru/products/currency/cb").get()
                val currency = document
                    .getElementsByClass("standard-table standard-table--row-highlight")
                    .select("tr td")

                val usd = currency[3].text()
                val eur = currency[8].text()

                val digits = Currency(null, usd, eur)
                addCurrency(digits)

            } catch (e: Exception) {
                Log.e("downloadData", e.toString())
            }
        }
    }
}