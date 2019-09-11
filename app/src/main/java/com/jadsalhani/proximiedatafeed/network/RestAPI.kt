package com.jadsalhani.proximiedatafeed.network

import com.jadsalhani.proximiedatafeed.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class RestAPI {
    val retrofit: Retrofit

    init {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(interceptor)
        }

        val client = builder.build()

        val baseUrl = "https://www.googleapis.com/books/v1/"

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}
