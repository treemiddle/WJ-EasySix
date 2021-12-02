package com.example.data.di

import com.example.data.AirQualityRepositoryImpl
import com.example.data.remote.AirQualityRemoteDataSource
import com.example.domain.repository.AirQualityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAirQuailtyRepository(airQualityRemoteDataSource: AirQualityRemoteDataSource): AirQualityRepository {
        return AirQualityRepositoryImpl(airQualityRemoteDataSource)
    }

}