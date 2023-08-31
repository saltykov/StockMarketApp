package com.example.stockmarketapp.presentation.company_listing

sealed class CompanyListingEvents{
    object Refresh: CompanyListingEvents()
    data class onSearchQueryChange(val query: String): CompanyListingEvents()
}