package com.example.investiq.domain.respository

import com.example.investiq.domain.model.CompanyDetail
import com.example.investiq.domain.model.CompanyItem
import com.example.investiq.domain.model.CompanyQuote
import com.example.investiq.domain.model.IntraDayInfo
import com.example.investiq.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListing(
        fetchFromRemote:Boolean, // should fetch data from remote if true or db if false
        query:String
    ): Flow<Resource<List<CompanyItem>>>
// here we cant use List<CompanyListingEntity> as domain and data layer should be independent of
// each other and should depend upon model of their own layer

    suspend fun getCompanyInfo(symbol:String):Resource<CompanyDetail>

    suspend fun getIntraDayInfo(symbol:String):Resource<List<IntraDayInfo>>

    suspend fun getCompanyPrice(symbol: String):Resource<CompanyQuote>
}