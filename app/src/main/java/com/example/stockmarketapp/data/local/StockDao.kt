package com.example.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockmarketapp.data.local.dbmodel.CompanyDbModel

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyDbModel: List<CompanyDbModel>
    )

    @Query("DELETE FROM companylistingstable")
    suspend fun clearCompanyListing()

    @Query("""
        SELECT * 
        FROM companylistingstable
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
            UPPER(:query) == symbol
    """)
    suspend fun searchCompanies(query: String): List<CompanyDbModel>
}