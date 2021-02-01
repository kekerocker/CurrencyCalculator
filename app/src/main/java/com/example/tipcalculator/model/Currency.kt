package com.example.tipcalculator.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "currency_table")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "usd")
    val usd: String,
    @ColumnInfo(name = "eur")
    val eur: String
): Parcelable