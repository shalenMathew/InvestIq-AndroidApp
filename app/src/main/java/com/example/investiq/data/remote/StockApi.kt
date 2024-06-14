package com.example.investiq.data.remote

import com.example.investiq.constant.Constant
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getStockList(
        @Query("apikey") apiKey:String=Constant.API_KEY
    ):ResponseBody   // thiz api will give us result in csv format instead of json
    // so thats the reason we dont need to create dto , as we will receive the data in byte stream instead of json

}