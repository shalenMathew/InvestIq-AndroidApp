package com.example.investiq.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavCompanyEntity(
    val symbol:String,
    val name:String,
    val exchangeShortName:String,
    val price:String,
    @PrimaryKey val id:Int?=null
)