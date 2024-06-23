package com.example.investiq.data.remote.dto

import com.squareup.moshi.Json

data class CompanyInfoDto(
    @field:Json(name = "Name") val name:String?,
    @field:Json(name = "AssetType") val assetType:String?,
    @field:Json(name = "Description") val description:String?,
    @field:Json(name = "Country") val country:String?,
    @field:Json(name = "Sector") val sector:String?,

)
