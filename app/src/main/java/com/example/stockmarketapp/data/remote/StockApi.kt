package com.example.stockmarketapp.data.remote

import com.example.stockmarketapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

    interface StockApi {

        @GET("query?function=LISTING_STATUS")
        suspend fun getCompanies(
            @Query("apikey") apiKey: String = API_KEY
        ) : ResponseBody

        @GET("query?function=TIME_SERIES_DAILY")
        suspend fun getDailyInfo(
            @Query("symbol") symbol: String,
            @Query("apikey") apiKey: String = API_KEY,
            @Query("datatype") datatype: String = DATA_TYPE_CSV
        ) : ResponseBody

        @GET("query?function=OVERVIEW")
        suspend fun getCompanyInfo(
            @Query("symbol") symbol: String,
            @Query("apikey") apiKey: String = API_KEY
        ) : CompanyInfoDto

        companion object {
            const val BASE_URL = "https://alphavantage.co"

            const val API_KEY = "83RI6Y3YCR9LKDH2"
            const val DATA_TYPE_CSV = "csv"
        }
    }