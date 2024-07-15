package com.example.investiq.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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

}