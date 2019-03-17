package com.giuseppebuzzanca.beerbox.di.module

import com.giuseppebuzzanca.beerbox.BuildConfig
import com.giuseppebuzzanca.beerbox.data.source.remote.PunkServices
import com.giuseppebuzzanca.beerbox.di.qualifier.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Singleton
    @Provides
    fun providesPunkServices(retrofit: Retrofit): PunkServices = retrofit.create(
        PunkServices::class.java
    )

    @Singleton
    @Provides
    @BaseUrl
    fun providesBaseUrl() = "https://api.punkapi.com/v2/"

    @Singleton
    @Provides
    fun providesCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.createAsync()

    @Singleton
    @Provides
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder().also {
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            it.addInterceptor(logger)
        }
    }.build()

    @Singleton
    @Provides
    fun providesRetrofit(
        @BaseUrl baseUrl: String, client: OkHttpClient,
        rxCallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxCallAdapterFactory)
            .build()

}