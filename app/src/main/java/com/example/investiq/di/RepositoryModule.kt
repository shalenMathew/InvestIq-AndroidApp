package com.example.investiq.di

import com.example.investiq.data.repository.FavCompanyRepositoryImpl
import com.example.investiq.data.repository.StockRepositoryImpl
import com.example.investiq.domain.respository.FavCompanyRepository
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

    // because an interface can have multiple classes implementing it, so hilt should know which class do u want the interface
    // to instantiate with


    @Singleton
    @Binds
    abstract fun bindsStockRepoImpl(stockRepositoryImpl: StockRepositoryImpl):StockRepository

    @Singleton
    @Binds
    abstract fun bindsFavCompanyRepoImpl(favCompanyRepositoryImpl: FavCompanyRepositoryImpl):FavCompanyRepository

}