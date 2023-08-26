package com.example.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

    interface StockApi {

        @GET("query?function=LISTING_STATUS")
        suspend fun getCompanies(
            @Query("apikey") apiKey: String = API_KEY
        ) : ResponseBody

        companion object {
            const val API_KEY = "83RI6Y3YCR9LKDH2"
            const val BASE_URL = "https://alphavantage.co"
        }
    }