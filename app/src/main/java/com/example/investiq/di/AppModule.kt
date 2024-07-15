package com.example.investiq.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.investiq.constant.Constant
import com.example.investiq.data.local.StockDatabase
import com.example.investiq.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
  fun  provideStockApi():StockApi{
      return  Retrofit.Builder()
          .baseUrl(Constant.FMP_BASE_URL)
          .addConverterFactory(MoshiConverterFactory.create())
          .build()
          .create()
    }

    @Singleton
    @Provides
    fun providesIStockDatabase(application: Application):StockDatabase{

        return Room.databaseBuilder(
            application
            ,StockDatabase::class.java
            ,"stock_database")
            .fallbackToDestructiveMigration()
            .build()

    }

}