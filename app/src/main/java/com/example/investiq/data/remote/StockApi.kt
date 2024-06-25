package com.example.investiq.data.remote


import com.example.investiq.BuildConfig
import com.example.investiq.data.remote.dto.CompanyInfoDto
import com.example.investiq.domain.model.CompanyInfo
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getStockList(
        @Query("apikey") apiKey:String=BuildConfig.API_KEY
    ):ResponseBody   // thiz api will give us result in csv format instead of json
    // so thats the reason we dont need to create dto , as we will receive the data in byte stream instead of json


    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol:String,
        @Query("apikey") apiKey:String = BuildConfig.API_KEY
    ):CompanyInfoDto

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): ResponseBody

}