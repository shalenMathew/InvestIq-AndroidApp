package com.example.investiq.data.mappers


import com.example.investiq.data.local.CompanyItemEntity
import com.example.investiq.data.local.FavCompanyEntity
import com.example.investiq.data.remote.dto.CompanyDetailDto
import com.example.investiq.data.remote.dto.CompanyItemDto
import com.example.investiq.data.remote.dto.CompanyQuoteDto
import com.example.investiq.data.remote.dto.IntraDayDto
import com.example.investiq.domain.model.CompanyDetail
import com.example.investiq.domain.model.CompanyItem
import com.example.investiq.domain.model.CompanyQuote
import com.example.investiq.domain.model.CompanyFavItem
import com.example.investiq.domain.model.IntraDayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


/// now making mappers that will map or convert the given dto class in data layer to model class in domain layer
// (abstraction)

fun CompanyFavItem.toFavCompanyEntity():FavCompanyEntity{

    return FavCompanyEntity(
        symbol = symbol,
        name = name,
        exchangeShortName = exchangeShortName,
        price = price,
        id=id
    )
}

fun CompanyItem.toCompanyFavItem():CompanyFavItem{

    return CompanyFavItem(
        symbol = symbol,
        name = name,
        exchangeShortName = exchangeShortName,
        price = price,
        id=id
    )

}


fun FavCompanyEntity.toFavCompanyItem():CompanyFavItem{

    return CompanyFavItem(symbol = symbol, name = name,price=price, exchangeShortName =exchangeShortName , id = id)

}

fun CompanyItemEntity.toCompanyItem():CompanyItem{
    return CompanyItem(
        symbol=symbol,
        name=name,
        exchangeShortName=exchangeShortName,
        price = price,
        id=id
    )
}

fun CompanyItem.toCompanyListingEntity():CompanyItemEntity{

    return CompanyItemEntity(
        symbol=symbol ?: "nullFromMappers",
        name=name ?: "null",
        exchangeShortName=exchangeShortName ?: "nullFromMappers",
        price = price ?: "nullFromMappers"
    )
}


fun IntraDayDto.toIntraDayInfo():IntraDayInfo{

    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localTimeFormatter = LocalDateTime.parse(date,formatter)

    return IntraDayInfo(
        close = close ?: 0.0,
        date =localTimeFormatter
    )
}

fun CompanyDetailDto.toCompanyDetail():CompanyDetail{

    return  CompanyDetail(
        companyName = companyName ?: "N/a from mappers" ,
        symbol = symbol ?: "N/A",
        image = image ?: "" ,
        dcf = dcf ?: 0.00,
    )
}

fun CompanyItemDto.toCompanyItem():CompanyItem{

    return CompanyItem(
        symbol = symbol ?: "nullFromMappers" ,
        name = name ?: "null",
        price = price ?:"null",
        exchangeShortName= exchangeShortName ?:"null"
    )
}


fun CompanyQuoteDto.toCompanyQuote():CompanyQuote{
    return CompanyQuote(
        changesPercentage = changesPercentage ?: 0.0,
        marketCap = marketCap ?: 0,
        pe = pe ?: 0.0 ,
        price = price ?: 0.0,
        avgVolume= avgVolume ?: 0,
        eps=eps?:0.0
    )
}



