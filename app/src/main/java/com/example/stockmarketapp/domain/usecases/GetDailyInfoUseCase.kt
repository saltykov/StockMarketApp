package com.example.stockmarketapp.domain.usecases

import com.example.stockmarketapp.domain.model.DailyInfo
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import javax.inject.Inject

class GetDailyInfoUseCase @Inject constructor(private val repository: StockRepository) {
    suspend operator fun invoke(symbol: String): Resource<List<DailyInfo>> {
        return repository.getDailyInfo(symbol)
    }
}