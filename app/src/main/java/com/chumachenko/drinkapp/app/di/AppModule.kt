package com.chumachenko.drinkapp.app.di

import androidx.room.Room
import com.chumachenko.core.common.notifier.AppNotifier
import com.chumachenko.core.data.storage.database.DrinkDatabase
import org.koin.dsl.module

val appModule = module {

    single { com.chumachenko.core.common.ResourceManager(get()) }

    single<DrinkDatabase> {
        Room.databaseBuilder(
            get(),
            DrinkDatabase::class.java, "drink-database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { AppNotifier() }
}