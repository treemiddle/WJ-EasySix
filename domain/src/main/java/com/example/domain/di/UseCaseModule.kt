package com.example.domain.di

import com.example.domain.repository.AirQualityRepository
import com.example.domain.repository.BigDataRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.AirQualityUseCase
import com.example.domain.usecase.BigDataUseCase
import com.example.domain.usecase.UserUseCase
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

    @Provides
    @Singleton
    fun provideBigDataUseCase(bigDataRepository: BigDataRepository): BigDataUseCase {
        return BigDataUseCase(bigDataRepository)
    }

    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(userRepository)
    }

}