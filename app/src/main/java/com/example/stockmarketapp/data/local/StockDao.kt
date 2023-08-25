package com.example.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockmarketapp.data.local.dbmodel.CompanyListingDbModel

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingDbModel: List<CompanyListingDbModel>
    )

    @Query("DELETE FROM companylistingtable")
    suspend fun clearCompanyListing()

    @Query("""
        SELECT * 
        FROM companylistingtable
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
            UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListing(query: String): List<CompanyListingDbModel>
}