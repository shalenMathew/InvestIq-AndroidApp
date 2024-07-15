package com.example.investiq.domain.model

data class CompanyQuote(
    val changesPercentage: Double,
    val marketCap: Long,
    val pe: Double,
    val price: Double,
    val avgVolume: Int,
    val eps: Double
)
