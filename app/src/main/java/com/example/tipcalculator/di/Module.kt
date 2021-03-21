package com.example.tipcalculator.di

import android.content.Context
import androidx.room.Room
import com.example.tipcalculator.data.CurrencyDatabase
import com.example.tipcalculator.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        CurrencyDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideCurrencyDao(db: CurrencyDatabase) = db.currencyDao()

}