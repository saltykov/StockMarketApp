package com.example.stockmarketapp.data.local.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingDbModel(
    @PrimaryKey val id: Int? = null,
    val symbol: String,
    val name: String,
    val exchange: String,
)