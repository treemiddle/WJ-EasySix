package com.example.remote.di

import com.example.remote.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AirQulityRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BigDataRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MockRetrofit

    private const val CONNECT_TIME = 3L
    private const val READ_TIME = 3L

    @AirQulityRetrofit
    @Provides
    @Singleton
    fun provideAirQualityRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AIR_QUALITY_BASE_URL)
            .client(provideOkHttp())
            .addCallAdapterFactory(provideRxAdapter())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
    }

    @BigDataRetrofit
    @Provides
    @Singleton
    fun provideBigDataRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BIG_DATA_BASE_URL)
            .client(provideOkHttp())
            .addCallAdapterFactory(provideRxAdapter())
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME, TimeUnit.SECONDS)
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .addInterceptor(provideOkHttpLogging())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideRxAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

}