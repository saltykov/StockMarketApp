package com.example.stockmarketapp.data.repository

import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.data.remote.StockApi
import com.example.stockmarketapp.data.remote.dto.DailyInfoDto
import com.example.stockmarketapp.data.remote.mapper.toCompany
import com.example.stockmarketapp.data.remote.mapper.toCompanyDbModel
import com.example.stockmarketapp.data.remote.mapper.toCompanyInfo
import com.example.stockmarketapp.data.remote.mapper.toDailyInfo
import com.example.stockmarketapp.domain.model.Company
import com.example.stockmarketapp.domain.model.CompanyInfo
import com.example.stockmarketapp.domain.model.DailyInfo
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import com.example.stockmarketapp.util.filterLast30Days
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<Company>,
    private val dailyInfoParser: CSVParser<DailyInfoDto>
) : StockRepository {

    private val dao = db.dao //DI
    override suspend fun getCompanies(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Company>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanies(query)
            emit(Resource.Success(
                data = localListings.map {
                    it.toCompany()
                }
            ))
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getCompanies()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let { companyListing ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(companyListing.map {
                    it.toCompanyDbModel()
                })
                emit(
                    Resource.Success(data = dao.searchCompanies("")
                        .map {
                            it.toCompany()
                        })
                )
                emit(Resource.Loading(false))

            }
        }
    }

    override suspend fun getDailyInfo(symbol: String): Resource<List<DailyInfo>> {
        return try {
            val response = api.getDailyInfo(symbol)
            val dailyInfoDto = dailyInfoParser.parse(response.byteStream())
            val dailyInfo = dailyInfoDto.map { it.toDailyInfo() }.filterLast30Days()

            Resource.Success(dailyInfo)

        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("IOException: ${e.message}")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("IOException: ${e.message}")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol)
            Resource.Success(
                response.toCompanyInfo()
            )
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("IOException: ${e.message}")
        } catch (e: HttpException){
            e.printStackTrace()
            Resource.Error("HttpException: ${e.message}")
        }
    }


}