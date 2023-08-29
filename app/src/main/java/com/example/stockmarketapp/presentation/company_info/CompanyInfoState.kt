package com.example.stockmarketapp.presentation.company_info

import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.DailyInfo

data class CompanyInfoState(
    val dailyInfo: List<DailyInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)