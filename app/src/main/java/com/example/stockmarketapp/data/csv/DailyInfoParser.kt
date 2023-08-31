package com.example.stockmarketapp.data.csv

import com.example.stockmarketapp.data.remote.dto.DailyInfoDto
import com.example.stockmarketapp.data.remote.mapper.toDailyInfo
import com.example.stockmarketapp.domain.model.DailyInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyInfoParser @Inject constructor() : CSVParser<DailyInfoDto> {
    override suspend fun parse(stream: InputStream): List<DailyInfoDto> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    DailyInfoDto(
                        timestamp,
                        close.toDouble()
                    )
                }
                .also {
                    csvReader.close()
                }
        }
    }
}