package com.example.investiq.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.investiq.data.local.CompanyListingEntity
import com.example.investiq.data.remote.dto.CompanyInfoDto
import com.example.investiq.data.remote.dto.IntradayInfoDto
import com.example.investiq.domain.model.CompanyInfo
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


/// now making mappers that will map or convert the given dto class in layer to model class in domain layer
// (abstraction)

fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return CompanyListing(
        symbol=symbol,
        name=name,
        exchange=exchange
    )
}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity{

    return CompanyListingEntity(
        symbol=symbol,
        name=name,
        exchange=exchange
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo():IntradayInfo{

    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTimeFormatter = LocalDateTime.parse(timestamp,formatter)


    return IntradayInfo(
        date =localDateTimeFormatter ,
        close = close
    )

}

fun CompanyInfoDto.toCompanyInfo():CompanyInfo{

    return CompanyInfo(
        name=name ?: "this api sucks!!!",
        assetType =assetType ?: "this api sucks!!!" ,
        description=description ?: "this api sucks!!!",
        country=country ?: "this api sucks!!!",
        sector=sector ?: "this api sucks!!!"
    )

}