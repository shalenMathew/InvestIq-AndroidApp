package com.example.investiq.domain.respository

import com.example.investiq.domain.model.CompanyFavItem
import kotlinx.coroutines.flow.Flow

interface FavCompanyRepository {

    suspend fun insertData(companyFavItem: CompanyFavItem)

    suspend fun deleteData(companyFavItem: CompanyFavItem)

    fun getAllFavCompany():Flow<List<CompanyFavItem>>

}