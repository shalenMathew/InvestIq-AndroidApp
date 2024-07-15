package com.example.investiq.data.remote


import com.example.investiq.BuildConfig
import com.example.investiq.data.remote.dto.CompanyDetailDto
import com.example.investiq.data.remote.dto.CompanyItemDto
import com.example.investiq.data.remote.dto.CompanyQuoteDto
import com.example.investiq.data.remote.dto.IntraDayDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockApi {


    // Alpha Vantage Api


    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String, @Query("apikey") apiKey: String = BuildConfig.API_KEY
    ): ResponseBody

    // Financial Modeling Prep api
    @GET("api/v3/stock/list")
    suspend fun getCompanyList( @Query("apikey") apiKey: String=BuildConfig.FMP_API_KEY): List<CompanyItemDto>

    @GET("api/v3/profile/{symbol}")
    suspend fun getCompanyInfo(@Path("symbol") symbol:String ,@Query("apikey") apiKey:String=BuildConfig.FMP_API_KEY):List<CompanyDetailDto>

    @GET("api/v3/quote/{symbol}")
    suspend fun getStockPrice(@Path("symbol") symbol:String,@Query("apikey") apiKey:String=BuildConfig.FMP_API_KEY):List<CompanyQuoteDto>

    @GET("api/v3/historical-chart/4hour/{symbol}")
    suspend fun getIntraDayInfo(@Path("symbol") symbol:String,
                                @Query("from") from:String,
                                @Query("to") to:String,
                                @Query("apikey") apiKey: String = BuildConfig.FMP_API_KEY):List<IntraDayDto>

//    https://financialmodelingprep.com/api/v3/historical-chart/1hour/AAPL?from=2024-05-07&to=2024-05-07&apikey=8v2NDAUDEkXY5gROnW9I6ycf5HGdT40l

}