package com.example.investiq.domain.model

import java.time.LocalDateTime

data class IntraDayInfo(
    val close: Double,
    val date: LocalDateTime
)
