package com.example.data.di

import com.example.data.*
import com.example.data.local.FinalLocalSource
import com.example.data.local.UserLocalDataSource
import com.example.data.remote.AirQualityRemoteDataSource
import com.example.data.remote.BigDataRemoteDataSource
import com.example.data.remote.FinalRemoteDataSource
import com.example.data.remote.MockRemoteDataSource
import com.example.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAirQuailtyRepository(airQualityRemoteDataSource: AirQualityRemoteDataSource): AirQualityRepository {
        return AirQualityRepositoryImpl(airQualityRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideBigDataRepository(bigDataRemoteDataSource: BigDataRemoteDataSource): BigDataRepository {
        return BigDataRepositoryImpl(bigDataRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userLocalDataSource: UserLocalDataSource): UserRepository {
        return UserRepositoryImpl(userLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideMockRepository(mockRemoteDataSource: MockRemoteDataSource): MockRepository {
        return MockRepositoryImpl(mockRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideFinalRepository(
        remoteDataSource: FinalRemoteDataSource,
        localDataSource: FinalLocalSource
    ): FinalRepository {
        return FinalRepositoryImpl(remoteDataSource, localDataSource)
    }

}