package com.example.investiq.presentation.company_info

import com.example.investiq.domain.model.CompanyInfo
import com.example.investiq.domain.model.IntradayInfo

data class CompanyInfoState (
    val intraDayInfo: List<IntradayInfo> = emptyList(),
    val companyInfo:CompanyInfo?=null,
    val isLoading:Boolean=false,
    val error:String?=null
)