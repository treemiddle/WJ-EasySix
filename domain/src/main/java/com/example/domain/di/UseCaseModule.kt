package com.example.domain.di

import com.example.domain.repository.AirQualityRepository
import com.example.domain.usecase.AirQualityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAirQualityUseCase(airQualityRepository: AirQualityRepository): AirQualityUseCase {
        return AirQualityUseCase(airQualityRepository)
    }

}