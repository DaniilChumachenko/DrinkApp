package com.chumachenko.drinkapp.app

import android.content.Context
import androidx.startup.Initializer
import com.chumachenko.drinkapp.app.di.appModule
import com.chumachenko.drinkapp.app.di.networkingModule
import com.chumachenko.drinkapp.app.di.screenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(
                listOf(
                    appModule,
                    networkingModule,
                    screenModule
                )
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
