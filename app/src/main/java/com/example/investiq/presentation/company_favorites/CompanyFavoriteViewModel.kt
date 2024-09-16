package com.example.investiq.presentation.company_favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investiq.domain.respository.FavCompanyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyFavoriteViewModel @Inject constructor(private val favCompanyRepository: FavCompanyRepository):ViewModel() {

    var state by mutableStateOf(CompanyFavoriteState())
   private var getFavCompJob:Job? = null

    init {
        getAllFavCompany()
    }

    fun onEvent(event: CompanyFavoriteEvent){

        when(event){

            is CompanyFavoriteEvent.insertData->{

                viewModelScope.launch {
                    favCompanyRepository.insertData(event.companyFavItem)
                }

            }
            is CompanyFavoriteEvent.deleteData->{

                viewModelScope.launch {
                    favCompanyRepository.deleteData(event.companyFavItem)
                }

            }
            is CompanyFavoriteEvent.getAllData->{
                getAllFavCompany()
            }

        }

    }


    private fun getAllFavCompany(){

        // -> launchIn is used to launch the collection of the Flow inside a specific coroutine scope.
        // Instead of manually calling collect, launchIn automatically starts collecting the Flow in the scope you specify.
        getFavCompJob?.cancel()

        state = state.copy(isLoading = true)

        getFavCompJob = viewModelScope.launch {

            favCompanyRepository.getAllFavCompany().onEach { dataList->
                state = state.copy(isLoading = false, data = dataList)
            }.launchIn(this)
        }

    }


}