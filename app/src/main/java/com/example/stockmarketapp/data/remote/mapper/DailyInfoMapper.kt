package com.example.stockmarketapp.data.remote.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.stockmarketapp.data.remote.dto.DailyInfoDto
import com.example.stockmarketapp.domain.model.DailyInfo
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale


fun DailyInfoDto.toDailyInfo(): DailyInfo {
    val dateFormatPattern = "yyyy-MM-dd"
    val formatter = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
    val date = formatter.parse(timeStamp)
    return DailyInfo(date, close)
}

