package com.example.investiq.data.repository

import com.example.investiq.data.csv.CSVParser
import com.example.investiq.data.local.StockDatabase
import com.example.investiq.data.mappers.toCompanyListing
import com.example.investiq.data.mappers.toCompanyListingEntity
import com.example.investiq.data.remote.StockApi
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.domain.respository.StockRepository
import com.example.investiq.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi,
    private val stockDb:StockDatabase,
    private val companyListingParser:CSVParser<CompanyListing>
):StockRepository {

    private val dao = stockDb.dao

    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ):Flow<Resource<List<CompanyListing>>> {

        return flow {
            emit(Resource.Loading())
            // taking data from cache
            val localListings = dao.searchForCompany(query)
            emit(Resource.Success(localListings.map {
                it.toCompanyListing()
            }))

            // checking if db is empty ...
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
                // return no need to fetch from api if the cache is not empty
            }

          val remoteListings =  try {
                val remoteListing = stockApi.getStockList()
                companyListingParser.parse(remoteListing.byteStream())

            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "IO error"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "HTTP error"))
                null
            }


            // now caching the data we gotten from our api

            remoteListings?.let { listOfcompanyListing->

                dao.deleteAllCompanyListings()
                // here down our insert fun in from data layer so should only take model from data layer
                dao.insertCompanyListing(
                    listOfcompanyListing.map {
                        it.toCompanyListingEntity()
                    }
                )

                emit(Resource.Success(
                    dao.searchForCompany("").map {
                        it.toCompanyListing()
                    }
                ))

                emit(Resource.Loading(false))
            }
        }

    }
}