package com.chumachenko.drinkapp.app.di

import androidx.room.Room
import com.chumachenko.core.common.ResourceManager
import com.chumachenko.core.common.notifier.AppNotifier
import com.chumachenko.core.data.storage.DrinksPreferencesManager
import com.chumachenko.core.data.storage.database.DrinkDatabase
import org.koin.dsl.module

val appModule = module {

    single { ResourceManager(get()) }

    single { DrinksPreferencesManager(get()) }

    single {
        Room.databaseBuilder(
            get(),
            DrinkDatabase::class.java, "drink-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { AppNotifier() }
}