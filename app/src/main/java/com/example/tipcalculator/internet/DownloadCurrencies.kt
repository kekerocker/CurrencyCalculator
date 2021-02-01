package com.example.tipcalculator.internet

import android.util.Log
import com.example.tipcalculator.model.Currency
import com.example.tipcalculator.viewmodel.CurrencyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class DownloadCurrencies(private val viewModel: CurrencyViewModel) {

    var usdCurrency = mutableListOf<String>()
    var eurCurrency = mutableListOf<String>()

    fun downloadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val document = Jsoup.connect("https://www.banki.ru/products/currency/cb").get()
                val currency = document
                    .getElementsByClass("standard-table standard-table--row-highlight")
                    .select("tr td")

                val usd = currency[3].text()
                val eur = currency[8].text()
                usdCurrency.add(usd)
                eurCurrency.add(eur)

                withContext(Dispatchers.Main) {
                    val digits = Currency(null, usd, eur)
                    viewModel.addCurrency(digits)
                }
            } catch (e: Exception) {
                Log.e("downloadData", e.toString())
            }
        }
    }
}