package com.example.remote.di

import com.example.remote.api.AirQualityApi
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
    fun provideAirQualityApi(retrofit: Retrofit): AirQualityApi {
        return retrofit.create(AirQualityApi::class.java)
    }

}