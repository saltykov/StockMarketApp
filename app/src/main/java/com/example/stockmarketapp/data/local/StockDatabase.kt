package com.example.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockmarketapp.data.local.dbmodel.CompanyDbModel

@Database(
    entities = [CompanyDbModel::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
}