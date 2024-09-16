package com.example.investiq.domain.model

data class CompanyFavItem(
    val symbol:String,
    val name:String,
    val price:String,
    val exchangeShortName:String,
    val id:Int?=null
)