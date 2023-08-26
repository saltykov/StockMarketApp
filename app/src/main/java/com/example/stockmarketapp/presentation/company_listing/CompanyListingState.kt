package com.example.stockmarketapp.presentation.company_listing

import com.example.stockmarketapp.domain.model.Company

data class CompanyListingState(
    val companies: List<Company> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
) {
}