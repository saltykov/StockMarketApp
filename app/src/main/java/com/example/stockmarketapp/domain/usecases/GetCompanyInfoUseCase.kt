package com.example.stockmarketapp.domain.usecases

import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import javax.inject.Inject

class GetCompanyInfoUseCase @Inject constructor(private val repository: StockRepository) {
    suspend operator fun invoke(symbol: String): Resource<CompanyInfo> {
        return repository.getCompanyInfo(symbol)
    }
}