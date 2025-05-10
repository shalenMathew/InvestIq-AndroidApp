package com.example.investiq.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.investiq.data.local.StockDatabase
import com.example.investiq.data.mappers.toCompanyDetail

import com.example.investiq.data.mappers.toCompanyItem
import com.example.investiq.data.mappers.toCompanyListingEntity
import com.example.investiq.data.mappers.toCompanyQuote
import com.example.investiq.data.mappers.toIntraDayInfo
import com.example.investiq.data.remote.StockApi
import com.example.investiq.data.remote.dto.CompanyItemDto
import com.example.investiq.domain.model.CompanyDetail
import com.example.investiq.domain.model.CompanyItem
import com.example.investiq.domain.model.CompanyQuote
import com.example.investiq.domain.model.IntraDayInfo
import com.example.investiq.domain.respository.StockRepository
import com.example.investiq.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.time.LocalDate
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val stockApi: StockApi,
    private val stockDb:StockDatabase,
):StockRepository {

    private val dao = stockDb.dao

    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ):Flow<Resource<List<CompanyItem>>> {

        return flow {

            emit(Resource.Loading(isLoading = true))

            // taking data from cache
            val localListings = dao.searchForCompany(query)

            // checking if db is empty ...
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote // if db is not empty and and not fetchFromRemote we will
                // just get data from cache one issue that can arise from this is we can only make limited call from this api
            // once the limit is over api will return empty list  instead of an error message indicating that limit it is over

            // now if the db is empty it wont get from cache anymore as it will skip the below condition

//            Log.d("TAG_from_StockRepoImpl", "fetch from remote - $fetchFromRemote ")
//            Log.d("TAG_from_StockRepoImpl","shouldJustLoadFromCache - $shouldJustLoadFromCache")

            if (shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                Log.d("TAG", "data from db ")

                emit(Resource.Success(localListings.map {
                    it.toCompanyItem()
                }))

                return@flow // this will return only from flow not from entire function
                // return from here coz , no need to fetch from api if the cache is not empty
            }

            Log.d("FLOW","before api call")

          val remoteListings =  try {

             stockApi.getCompanyList()

            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "IO error"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "unknown error occurred"))
                null
            } catch (e:UnknownHostException){
              e.printStackTrace()
              emit(Resource.Error("No network connection...Try to refresh..."))
              null
          } catch (e:Exception){
              e.printStackTrace()
              emit(Resource.Error(e.message ?: "unknown error occurred"))
              null
            }
            Log.d("FLOW","after api call")

            // now caching the data we gotten from our api



            // deleting existing data if any
            remoteListings?.let { listOfcompanyListing->

                val sampleList = mutableListOf<CompanyItemDto>()

                Log.d("FLOW","before delete")

                dao.deleteAllCompanyListings()
                // here down our insert fun in from data layer so should only take model from data layer

                Log.d("FLOW","after delete delete")

           listOfcompanyListing.filter {
               it.exchangeShortName== "NASDAQ"
           }.forEach { item->
                   sampleList.add(item)
           }


                Log.d("FLOW","LIST SIZE - ${sampleList.size}")
                Log.d("FLOW","Before insert")
                // inserting new data
                dao.insertCompanyListing(
                sampleList.map {
                    it.toCompanyItem().toCompanyListingEntity()
                }
                )

                Log.d("FLOW","after insert")

                Log.d("FLOW","before emit")

                // emitting the data
                emit(Resource.Success(
                    dao.searchForCompany("").map {
                        it.toCompanyItem()
                    }
                ))

                Log.d("FLOW","after emit")

                emit(Resource.Loading(false))

            }?:run { Log.d("stock_repo_impl","remote listing is null") }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {

        return try{
            val from = LocalDate.now().minusDays(7).toString()
            val to = LocalDate.now().minusDays(3).toString()
            Log.d("from",from)
            Log.d("to",to)
            val intraDayInfoResult = stockApi.getIntraDayInfo(symbol=symbol, from =from , to = to)

            Resource.Success(intraDayInfoResult.map { it.toIntraDayInfo() })

        }catch (e:Exception){
            return Resource.Error(message = e.message.toString())
        }

    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyDetail> {
        try {
            val companyDetailResult = stockApi.getCompanyInfo(symbol)
            return Resource.Success(data = companyDetailResult[0].toCompanyDetail())

        }catch (e:Exception){
            return Resource.Error(message = e.message.toString())
        }
    }

    override suspend fun getCompanyPrice(symbol: String): Resource<CompanyQuote> {
      return  try {
            val stockPriceResult = stockApi.getStockPrice(symbol)
             Resource.Success(data = stockPriceResult[0].toCompanyQuote())
        }catch (e:Exception){
           Resource.Error(message = e.message.toString())
      }
    }


}