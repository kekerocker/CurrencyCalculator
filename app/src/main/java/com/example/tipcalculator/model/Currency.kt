package com.example.tipcalculator.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val usd: String?,
    val eur: String?
): Parcelable