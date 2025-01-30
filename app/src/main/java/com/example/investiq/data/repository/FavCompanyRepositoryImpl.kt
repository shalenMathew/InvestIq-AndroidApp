package com.example.investiq.data.repository

import android.util.Log
import com.example.investiq.data.local.StockDao
import com.example.investiq.data.mappers.toFavCompanyEntity
import com.example.investiq.data.mappers.toFavCompanyItem
import com.example.investiq.domain.model.CompanyFavItem
import com.example.investiq.domain.respository.FavCompanyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavCompanyRepositoryImpl @Inject constructor(private val dao:StockDao):FavCompanyRepository {

    override suspend fun insertData(companyFavItem: CompanyFavItem) {
        dao.insertFavCompany(companyFavItem.toFavCompanyEntity())
    }

    override suspend fun deleteData(companyFavItem: CompanyFavItem) {
        Log.d("FavCompRepoImpl","Delete fun is being called-$companyFavItem")
       dao.deleteFavCompany(companyFavItem.toFavCompanyEntity())
    }

    override fun getAllFavCompany(): Flow<List<CompanyFavItem>>{
        return dao.getAllFavCompanyList().map { companyEntityList ->
            companyEntityList.map { item ->
                item.toFavCompanyItem()
            }
        }
        }
    }