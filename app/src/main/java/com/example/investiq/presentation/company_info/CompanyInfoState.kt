package com.example.investiq.presentation.company_info

import com.example.investiq.domain.model.CompanyDetail
import com.example.investiq.domain.model.CompanyQuote
import com.example.investiq.domain.model.IntraDayInfo

data class CompanyInfoState (
    val intraDayInfo: List<IntraDayInfo> = emptyList(),
    val companyDetail:CompanyDetail?=null,
    val stockPrice:CompanyQuote?=null,
    val isLoading:Boolean=false,
    val error:String?=null
)