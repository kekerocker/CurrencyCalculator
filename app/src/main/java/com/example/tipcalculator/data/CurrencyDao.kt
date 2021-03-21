package com.example.tipcalculator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tipcalculator.model.Currency

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCurrency(currency: Currency)

    @Query("DELETE FROM currency_table")
    suspend fun deleteAllCurrencies()

    @Query("SELECT * FROM currency_table")
    fun readAllData(): LiveData<Currency>
}