package com.chumachenko.core.data.storage.database.dao

import androidx.room.*
import com.chumachenko.core.data.storage.database.entity.SearchHistory

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history ORDER BY saved_at DESC")
    suspend fun getAll(): List<SearchHistory>

    @Query("SELECT * FROM search_history ORDER BY saved_at DESC LIMIT 15")
    suspend fun getLast15(): List<SearchHistory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: SearchHistory)

    @Delete
    suspend fun delete(searchHistory: SearchHistory)

}