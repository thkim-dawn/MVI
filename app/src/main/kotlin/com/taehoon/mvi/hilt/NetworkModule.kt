package com.taehoon.mvi.hilt

import com.taehoon.mvi.BuildConfig
import com.taehoon.mvi.datasource.api.PicsumApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providePicsumApi(): PicsumApi {
        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .client(createHttpClientBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicsumApi::class.java)
    }

    private fun createHttpClientBuilder(): OkHttpClient.Builder {
        val httpClientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        httpClientBuilder.readTimeout(3000, TimeUnit.MILLISECONDS)
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder
    }
}