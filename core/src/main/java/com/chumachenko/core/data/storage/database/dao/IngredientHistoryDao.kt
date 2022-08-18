package com.chumachenko.core.data.storage.database.dao

import androidx.room.*
import com.chumachenko.core.data.storage.database.entity.IngredientHistory
import com.chumachenko.core.data.storage.database.entity.SearchHistory

@Dao
interface IngredientHistoryDao {

    @Query("SELECT * FROM ingredient_history ORDER BY saved_at DESC")
    suspend fun getAll(): List<IngredientHistory>

    @Query("SELECT * FROM ingredient_history WHERE title=:query")
    suspend fun getIngredientByQuery(query: String): IngredientHistory?

    @Query("DELETE FROM ingredient_history")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchHistory: IngredientHistory)

    @Delete
    suspend fun delete(searchHistory: IngredientHistory)

}