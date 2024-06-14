package com.example.investiq.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CompanyListingEntity::class], version = 1)
abstract class StockDatabase:RoomDatabase() {
    abstract val dao:StockDao
}