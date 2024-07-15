package com.example.investiq.data.remote.dto

data class CompanyQuoteDto(
    val avgVolume: Int?,
    val change: Double?,
    val changesPercentage: Double?,
    val dayHigh: Double?,
    val dayLow: Double?,
    val earningsAnnouncement: String?,
    val eps: Double?,
    val exchange: String?,
    val marketCap: Long?,
    val name: String?,
    val open: Double?,
    val pe: Double?,
    val previousClose: Double?,
    val price: Double?,
    val priceAvg200: Double?,
    val priceAvg50: Double?,
    val sharesOutstanding: Long?,
    val symbol: String?,
    val timestamp: Int?,
    val volume: Int?,
    val yearHigh: Double?,
    val yearLow: Double?
)