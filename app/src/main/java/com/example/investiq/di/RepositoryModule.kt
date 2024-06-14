package com.example.investiq.di

import com.example.investiq.data.csv.CSVParser
import com.example.investiq.data.csv.CompanyListingParser
import com.example.investiq.data.repository.StockRepositoryImpl
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.domain.respository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // when using @Inject constructor with Interface implementation u need to use @Bind to tell hilt how to
    // instantiate the interface

    @Singleton
    @Binds
    abstract fun bindsCompanyListingParser(companyListingParser: CompanyListingParser):CSVParser<CompanyListing>

    @Singleton
    @Binds
    abstract fun bindsStockRepoImpl(stockRepositoryImpl: StockRepositoryImpl):StockRepository

}