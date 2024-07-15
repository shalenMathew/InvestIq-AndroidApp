package com.example.investiq.presentation.company_list

import com.example.investiq.domain.model.CompanyItem

data class CompanyListingState(
    val companyList:List<CompanyItem> = emptyList(),
    val isRefreshing:Boolean=false,
    val isLoading:Boolean=false,
    val searchQuery:String = "",
    val error:String =""
)