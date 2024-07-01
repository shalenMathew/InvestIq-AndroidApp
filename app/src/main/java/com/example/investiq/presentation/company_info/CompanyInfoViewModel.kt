package com.example.investiq.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investiq.domain.respository.StockRepository
import com.example.investiq.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    var state:CompanyInfoState by mutableStateOf(CompanyInfoState())

    init {
        viewModelScope.launch {

            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)

//            val companyInfo = stockRepository.getCompanyInfo(symbol)
//            val intraDayInfo = stockRepository.getIntraDayInfo(symbol)
            // as we know can call this thread blocking fun in coroutine
            // but as we the know coroutine runs line by line which means we wont get result for intraDayInfo until
            // we get the the result for companyInfo , as we can see both are unrelated task so we can call this simultaneously

            val companyInfoResult =  async {stockRepository.getCompanyInfo(symbol)}
            val intraDayInfoResult = async {stockRepository.getIntraDayInfo(symbol)}

            when(val result = companyInfoResult.await()){
                is Resource.Error -> state=state.copy(isLoading = false, error = result.message)
                is Resource.Loading -> state= state.copy(isLoading = true)
                is Resource.Success -> {
                    state = state.copy(companyInfo = result.data, isLoading = false, error = "" )
                }
            }

            when(val result = intraDayInfoResult.await()){
                is Resource.Error -> state=state.copy(isLoading = false, error = result.message)
                is Resource.Loading -> state= state.copy(isLoading = true)
                is Resource.Success -> {
                    state = state.copy(intraDayInfo = result.data ?: emptyList(), isLoading = false, error = "" )
                }
            }

        }
    }
}