package com.chumachenko.drinkapp.app.di

import com.chumachenko.core.data.networking.AppApi
import com.chumachenko.core.data.networking.CoreApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.chumachenko.drinkapp.BuildConfig.BASE_URL

val networkingModule = module {

    single { com.chumachenko.core.common.NetworkUtils(get()) }

    single<Gson> {
        GsonBuilder().create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
//            addInterceptor(HeadersInterceptor(get()))
//            if (BuildConfig.DEBUG) {
//                addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            }
//            addInterceptor(HandleErrorInterceptor(get(), get(), get(), get()))
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { provideApi<AppApi>(get()) }
    single { provideApi<CoreApi>(get()) }

}

inline fun <reified T> provideApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}