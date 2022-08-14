package com.chumachenko.core.data.storage.database.dao

import androidx.room.*
import com.chumachenko.core.data.storage.database.entity.DrinksHistory
import com.chumachenko.core.data.storage.database.entity.SearchHistory

@Dao
interface DrinksHistoryDao {

    @Query("SELECT * FROM drinks_history ORDER BY saved_at DESC")
    suspend fun getAll(): List<DrinksHistory>

    @Query("SELECT * FROM drinks_history ORDER BY saved_at DESC LIMIT 30")
    suspend fun getLast(): List<DrinksHistory>

    @Query("DELETE FROM drinks_history")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(drinksHistory: DrinksHistory)

}