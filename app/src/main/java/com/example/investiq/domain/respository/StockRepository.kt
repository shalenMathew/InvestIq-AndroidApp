package com.example.investiq.domain.respository

import com.example.investiq.domain.model.CompanyInfo
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.domain.model.IntradayInfo
import com.example.investiq.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote:Boolean, // should fetch data from remote if true or db if false
        query:String
    ): Flow<Resource<List<CompanyListing>>>
// here we cant use List<CompanyListingEntity> as domain and data layer should be independent of
// each other and should depend upon model of their own layer

    suspend fun getIntraDayInfo(symbol:String):Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(symbol:String):Resource<CompanyInfo>


}