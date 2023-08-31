package com.example.stockmarketapp.domain.repository

import com.example.stockmarketapp.domain.model.Company
import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.DailyInfo
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanies(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Company>>>

    suspend fun getDailyInfo(
        symbol: String
    ): Resource<List<DailyInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>


}