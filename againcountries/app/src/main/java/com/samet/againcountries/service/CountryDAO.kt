package com.samet.againcountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.samet.againcountries.model.Country

@Dao
interface CountryDAO {
    //Data access Object

    @Insert
    suspend fun insertAll(vararg countries: Country):List<Long>

    //Insert-> Database
    //suspend -> Coroutine, pause and resume
    //vararg -> multiple country object
    //List<Long> -> primary keys

    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>

    @Query("SELECT * FROM country WHERE uuid= :countryId")
    suspend fun getCountry(countryId: Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}