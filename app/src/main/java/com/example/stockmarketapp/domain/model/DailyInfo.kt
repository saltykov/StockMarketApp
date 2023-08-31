package com.example.stockmarketapp.domain.model

import java.util.Date

data class DailyInfo(
    val date: Date,
    val close: Double
)