package com.example.investiq.presentation.company_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investiq.domain.respository.StockRepository
import com.example.investiq.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        viewModelScope.launch {

            stockRepository.getCompanyListing(fetchFromRemote, query).collect{

                when(it){

                    is Resource.Success->{

                        state = if (it.data.isNullOrEmpty()){
                            state.copy(error="The list is empty")
                        }else{
                            state.copy(companyList = it.data)
                        }

                    }

                    is Resource.Loading->{
                        state=state.copy(isLoading = true)
                    }

                    is Resource.Error-> {
                        state=state.copy(error=it.message!!)
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