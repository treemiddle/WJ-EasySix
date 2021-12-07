package com.example.remote.di

import com.example.remote.api.AirQualityApi
import com.example.remote.api.BigDataApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideAirQualityApi(@NetworkModule.AirQulityRetrofit retrofit: Retrofit): AirQualityApi {
        return retrofit.create(AirQualityApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBigDataApi(@NetworkModule.BigDataRetrofit retrofit: Retrofit): BigDataApi {
        return retrofit.create(BigDataApi::class.java)
    }

}