package com.example.stockmarketapp.domain.model

data class CompanyListing(
    val symbol: String,
    val name: String,
    val exchange: String
)