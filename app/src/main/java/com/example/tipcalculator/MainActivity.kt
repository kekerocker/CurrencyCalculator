package com.example.tipcalculator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private val url = "https://www.banki.ru/products/currency/cb"

    private var usdCurrency = mutableListOf<String>()
    private var eurCurrency = mutableListOf<String>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.calculateButton.setOnClickListener { calculateTip() }
        downloadCurrency()
    }

    private fun downloadCurrency() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val document = Jsoup.connect(url).get()
                val currency = document
                    .getElementsByClass("standard-table standard-table--row-highlight")
                    .select("tr td")

                val usd = currency[3].text()
                val eur = currency[8].text()
                usdCurrency.add(usd)
                eurCurrency.add(eur)

                withContext(Dispatchers.Main) {
                    putCurrency()
                }

            } catch (e: Exception) {
                Log.e("downloadData", e.toString())
            }
        }
    }

    private fun putCurrency() {
        tv_usd.text = "Сurrent cost USD: ${usdCurrency[0]}"
        tv_eur.text = "Сurrent cost EUR: ${eurCurrency[0]}"
    }

    private fun calculateTip() {
        val stringInTextField = tv_count.text.toString()
        val cost = stringInTextField.toDouble()
        val usd = usdCurrency[0].toDouble()
        val eur = eurCurrency[0].toDouble()
        var result = eur * cost

        when (spinner.selectedItem) {
            "RUB-USD" -> result = cost / usd
            "RUB-EUR" -> result = cost / eur
            "EUR-RUB" -> result = eur * cost
            "USD-RUB" -> result = usd * cost
        }
        tv_result.text = "Result: $result"
    }
}