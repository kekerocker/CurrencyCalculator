package com.example.tipcalculator.ui.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class CurrencyViewModelTest {

    @Test
    fun calculateTip() {
        lateinit var mathResult: BigDecimal

        val number = 435.toBigDecimal()
        val usd = 25.toBigDecimal()
        val eur = 125.toBigDecimal()
        val spinnerResult = "RUB-USD"
        when (spinnerResult) {
            "RUB-USD" -> mathResult = number / usd
            "RUB-EUR" -> mathResult = number / eur
            "EUR-RUB" -> mathResult = number * eur
            "USD-RUB" -> mathResult = number * usd
        }
        assertEquals(mathResult, 17.toBigDecimal())
    }
}