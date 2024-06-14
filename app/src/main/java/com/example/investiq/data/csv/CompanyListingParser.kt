package com.example.investiq.data.csv

import com.example.investiq.domain.model.CompanyListing
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class CompanyListingParser @Inject constructor():CSVParser<CompanyListing> {

    override suspend fun parse(stream: InputStream): List<CompanyListing> {

        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO){

            csvReader
                .readAll()
                .drop(1)
                .mapNotNull {
                    val symbol=it.getOrNull(0)
                    val name = it.getOrNull(1)
                    val exchange = it.getOrNull(2)

                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )

                } .also {
                    csvReader.close()
                }
        }


    }
}