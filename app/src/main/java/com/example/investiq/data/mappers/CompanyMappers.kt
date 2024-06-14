package com.example.investiq.data.mappers

import com.example.investiq.data.local.CompanyListingEntity
import com.example.investiq.domain.model.CompanyListing


/// now making mappers that will map or convert the given model class to another model class

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