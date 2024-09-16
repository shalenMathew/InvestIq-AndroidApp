package com.example.investiq.presentation.company_favorites

import com.example.investiq.domain.model.CompanyFavItem

sealed class CompanyFavoriteEvent {
    class insertData(val companyFavItem: CompanyFavItem):CompanyFavoriteEvent()
    class deleteData(val companyFavItem: CompanyFavItem):CompanyFavoriteEvent()
    data object getAllData : CompanyFavoriteEvent()
}