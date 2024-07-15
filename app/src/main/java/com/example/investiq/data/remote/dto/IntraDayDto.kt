package com.example.investiq.data.remote.dto

data class IntraDayDto(
    val close: Double?,
    val date: String?,
    val high: Double?,
    val low: Double?,
    val open: Double?,
    val volume: Int?
)