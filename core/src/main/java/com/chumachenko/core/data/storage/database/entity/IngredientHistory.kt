package com.chumachenko.core.data.storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ingredient_history")
data class IngredientHistory(
    @PrimaryKey val title: String,
    @ColumnInfo(name = "saved_at") val savedAt: Long
)