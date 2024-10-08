package com.example.investiq.presentation.company_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investiq.domain.respository.StockRepository
import com.example.investiq.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CompanyListingViewmodel @Inject constructor(private val stockRepository: StockRepository):ViewModel() {

var state by mutableStateOf(CompanyListingState())

  private var searchJob: Job?=null

    init {
        getCompanyListing()
    }

   fun  getCompanyListing(
        query:String = state.searchQuery.lowercase(),
        fetchFromRemote:Boolean = false
    ){
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("TAG",Thread.currentThread().name)
            stockRepository.getCompanyListing(fetchFromRemote, query).collect{

                withContext(Dispatchers.Main){

                    Log.d("TAG",Thread.currentThread().name)

                    when(it){

                        is Resource.Success->{
                            state = if (it.data.isNullOrEmpty()){
                                state.copy(isLoading = false,error="Nothing here to display... TRy to Refresh")
                            }else{
                                state.copy(isLoading = false,companyList = it.data, error = "")
                            }
                        }
                        is Resource.Loading->{
                            state=state.copy(isLoading = it.isLoading)
                        }

                        is Resource.Error-> {
                            state=state.copy(error=it.message!!, isLoading = false)
                        }

                    }
                }
            }
        }
    }

   fun onEvent(companyListingEvent: CompanyListingEvent){

        when(companyListingEvent){

            is CompanyListingEvent.OnSearchQueryChanged->{

                state = state.copy(searchQuery = companyListingEvent.query)

                searchJob?.cancel()

                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListing()
                }

            }

            is CompanyListingEvent.Refresh->{
                getCompanyListing(fetchFromRemote = true)
            }
        }
    }

}