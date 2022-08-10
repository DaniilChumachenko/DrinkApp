package com.chumachenko.core.data.storage.database.dao

import androidx.room.*
import com.chumachenko.core.data.storage.database.entity.SearchGooglePlaceHistory

@Dao
interface SearchGooglePlaceHistoryDao {

    @Query("SELECT * FROM search_google_place_history ORDER BY saved_at DESC")
    suspend fun getAll(): List<SearchGooglePlaceHistory>

    @Query("SELECT * FROM search_google_place_history ORDER BY saved_at DESC LIMIT 10")
    suspend fun getLast10(): List<SearchGooglePlaceHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: SearchGooglePlaceHistory)

    @Delete
    suspend fun delete(searchHistory: SearchGooglePlaceHistory)

}