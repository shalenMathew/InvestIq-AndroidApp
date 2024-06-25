package com.example.investiq.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.investiq.data.mappers.toIntradayInfo
import com.example.investiq.data.remote.dto.IntradayInfoDto
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject

class CompanyIntrDayParser @Inject constructor():CSVParser<IntradayInfo>{


    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO){

            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line->
                    // this is a map that will transform the list

                    // csv files return the list of strings

                    // so 'line' here indicate items in the list


                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null // here if the element at 0 is empty we will add it as null
                    // if its null 'return@mapNotNull null' condition will run which will exit or return from  the lamda function
                    // just like the return function that we use .... but here ts just return from mapNotNull lambda block if it encounters null
                    val close = line.getOrNull(4) ?: return@mapNotNull null

                    val intraDayInfoDto = IntradayInfoDto(timestamp,close.toDouble())
                    intraDayInfoDto.toIntradayInfo()


                }  .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }

                .also {
                    csvReader.close()
                }



        }
    }


}