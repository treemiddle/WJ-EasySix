package com.example.local.di

import com.example.data.local.FinalLocalSource
import com.example.data.local.UserLocalDataSource
import com.example.local.FinalLocalDataSourceImpl
import com.example.local.UserLocalDataSourceImpl
import com.example.local.dao.LabelDao
import com.example.local.prefs.PrefsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalSourceModule {

    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        prefsHelper: PrefsHelper
    ): UserLocalDataSource {
        return UserLocalDataSourceImpl(prefsHelper)
    }

    @Provides
    @Singleton
    fun provideFinalLocalDataSource(
        labelDao: LabelDao
    ): FinalLocalSource {
        return FinalLocalDataSourceImpl(labelDao)
    }

}