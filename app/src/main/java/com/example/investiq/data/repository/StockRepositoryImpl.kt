package com.example.investiq.data.repository

import android.util.Log
import com.example.investiq.data.csv.CSVParser
import com.example.investiq.data.local.StockDatabase
import com.example.investiq.data.mappers.toCompanyInfo
import com.example.investiq.data.mappers.toCompanyListing
import com.example.investiq.data.mappers.toCompanyListingEntity
import com.example.investiq.data.remote.StockApi
import com.example.investiq.domain.model.CompanyInfo
import com.example.investiq.domain.model.CompanyListing
import com.example.investiq.domain.model.IntradayInfo
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
    private val companyListingParser:CSVParser<CompanyListing>,
    private val intraDayInfoParser:CSVParser<IntradayInfo>
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
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote // if db is not empty and and not fetchFromRemote we will
                // just get data from cache one issue that can arise from this is we can only make limited call from this api
            // once the limit is over api will return empty list  instead of an error message indicating that limit it is over

            // now if the db is empty it wont get from cache anymore as it will skip the below condition

            Log.d("TAG", "fetch from remote - $fetchFromRemote ")
            Log.d("TAG","shouldJustLoadFromCache - $shouldJustLoadFromCache")

            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                Log.d("TAG", "data from db ")
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
            }catch (e:Exception){
              e.printStackTrace()
              null
            }


            // now caching the data we gotten from our api

            remoteListings?.let { listOfcompanyListing->

                Log.d("TAG", "data from remote ")

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

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntradayInfo>> {


        return  try {
            val intraDayResponse = stockApi.getIntradayInfo(symbol)
            val intraDayInfoResult = intraDayInfoParser.parse(intraDayResponse.byteStream())

            Resource.Success(data = intraDayInfoResult )

        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(message = e.message?: "An unknown error occured")
        }

    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        try {
            val companyInfoResult = stockApi.getCompanyInfo(symbol)

            return Resource.Success(data = companyInfoResult.toCompanyInfo())

        }catch (e:Exception){
            return Resource.Error(message = e.message.toString())
        }
    }
}