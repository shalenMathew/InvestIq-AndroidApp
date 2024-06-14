package com.example.investiq.presentation.company_list

import com.example.investiq.domain.model.CompanyListing

data class CompanyListingState(
    val companyList:List<CompanyListing> = emptyList(),
    val isRefreshing:Boolean=false,
    val isLoading:Boolean=false,
    val searchQuery:String = "",
    val error:String =""
)