package com.example.stockmarketapp.data.repository

import com.example.stockmarketapp.data.csv.CSVParser
import com.example.stockmarketapp.data.local.StockDatabase
import com.example.stockmarketapp.data.remote.StockApi
import com.example.stockmarketapp.data.remote.mapper.toCompany
import com.example.stockmarketapp.data.remote.mapper.toCompanyDbModel
import com.example.stockmarketapp.domain.model.Company
import com.example.stockmarketapp.domain.repository.StockRepository
import com.example.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingsParser: CSVParser<Company>
) : StockRepository {

    private val dao = db.dao
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
            remoteListings?.let {companyListing ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(companyListing.map {
                    it.toCompanyDbModel()
                })
                emit(Resource.Success(data = dao.searchCompanies("")
                    .map { it.toCompany()
                }))
                emit(Resource.Loading(false))

            }
        }
    }
}