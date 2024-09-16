package com.example.investiq.domain.model

import androidx.room.PrimaryKey

data class CompanyItem(
    val symbol:String,
    val name:String,
    val price:String,
    val exchangeShortName:String,
    val id :Int?=null
)
