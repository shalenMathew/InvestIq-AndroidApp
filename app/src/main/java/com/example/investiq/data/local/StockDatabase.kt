package com.example.investiq.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompanyItemEntity::class], version = 2)
abstract class StockDatabase:RoomDatabase() {
    abstract val dao:StockDao
}