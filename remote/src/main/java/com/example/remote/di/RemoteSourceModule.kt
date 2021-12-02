package com.example.remote.di

import com.example.data.remote.AirQualityRemoteDataSource
import com.example.remote.AirQualityRemoteDataSourceImpl
import com.example.remote.api.AirQualityApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteSourceModule {

    @Provides
    @Singleton
    fun provideAirQualityRemoteSource(airQualityApi: AirQualityApi): AirQualityRemoteDataSource {
        return AirQualityRemoteDataSourceImpl(airQualityApi)
    }

}