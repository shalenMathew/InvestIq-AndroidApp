package com.example.investiq.presentation.company_favorites

import com.example.investiq.domain.model.CompanyFavItem

data class CompanyFavoriteState(
    val isLoading:Boolean=false,
    val data:List<CompanyFavItem> = emptyList(),
    val error:String=""
    )