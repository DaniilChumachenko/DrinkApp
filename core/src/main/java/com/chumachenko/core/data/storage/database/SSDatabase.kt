package com.chumachenko.core.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chumachenko.core.data.storage.database.dao.SearchGooglePlaceHistoryDao
import com.chumachenko.core.data.storage.database.dao.SearchHistoryDao
import com.chumachenko.core.data.storage.database.entity.SearchGooglePlaceHistory
import com.chumachenko.core.data.storage.database.entity.SearchHistory

@Database(entities = [SearchHistory::class, SearchGooglePlaceHistory::class], version = DrinkDatabase.DB_VERSION, exportSchema = false)
abstract class DrinkDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao

    abstract fun searchGooglePlaceHistoryDao(): SearchGooglePlaceHistoryDao

    companion object {
        const val DB_VERSION = 1
    }
}