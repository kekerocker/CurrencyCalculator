package com.example.tipcalculator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tipcalculator.model.Currency

@Database(entities = [Currency::class], version = 2, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}