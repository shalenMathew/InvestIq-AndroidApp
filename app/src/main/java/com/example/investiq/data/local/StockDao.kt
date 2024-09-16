package com.example.investiq.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.investiq.domain.model.CompanyItem
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCompanyListing(companyListings:List<CompanyItemEntity>)
   @Query(" DELETE FROM companyitementity")
   suspend fun deleteAllCompanyListings()
   @Query("""  SELECT * FROM companyitementity 
               WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' 
                 OR
       UPPER(:query) == symbol """)
   suspend fun searchForCompany(query:String):List<CompanyItemEntity>


   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertFavCompany(favCompanyEntity: FavCompanyEntity)
   @Delete
   suspend fun deleteFavCompany(favCompanyEntity: FavCompanyEntity)
   @Query(" SELECT * FROM FAVCOMPANYENTITY")
   fun getAllFavCompanyList():Flow<List<FavCompanyEntity>>
}