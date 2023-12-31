package com.example.stockmarketapp.di

import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.csv.CompanyListingsParser
import com.example.stockmarketapp.data.csv.DailyInfoParser
import com.example.stockmarketapp.data.remote.dto.DailyInfoDto
import com.example.stockmarketapp.data.repository.StockRepositoryImpl
import com.example.stockmarketapp.domain.model.Company
import com.example.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsCompanyCSVParser(impl: CompanyListingsParser): CSVParser<Company>
    @Binds
    @Singleton
    abstract fun bindsDailyInfoCSVParser(impl: DailyInfoParser): CSVParser<DailyInfoDto>

    @Binds
    @Singleton
    abstract fun bindsRepository(impl: StockRepositoryImpl): StockRepository

}