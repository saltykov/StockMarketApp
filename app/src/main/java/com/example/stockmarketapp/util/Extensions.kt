package com.example.stockmarketapp.util

import com.example.stockmarketapp.domain.model.DailyInfo
import java.util.Calendar

fun List<DailyInfo>.filterLast30Days(): List<DailyInfo> {
    val calendar = Calendar.getInstance().apply {
        add(Calendar.DATE, -30)
    }
    val startDate = calendar.time
    return this.filter {
        it.date >= startDate
    }
}