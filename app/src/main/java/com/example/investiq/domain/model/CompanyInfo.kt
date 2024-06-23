package com.example.investiq.domain.model

import com.squareup.moshi.Json

data class CompanyInfo(
  val name:String,
  val assetType:String,
 val description:String,
 val country:String,
 val sector:String,
)
