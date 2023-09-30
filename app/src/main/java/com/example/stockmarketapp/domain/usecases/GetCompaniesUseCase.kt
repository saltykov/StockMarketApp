package com.example.stockmarketapp.domain.usecases

import com.example.stockmarketapp.domain.model.Company
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompaniesUseCase @Inject constructor(private val repository: StockRepository) {
    suspend operator fun invoke(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Company>>>{
        return repository.getCompanies(fetchFromRemote, query)
    }
}