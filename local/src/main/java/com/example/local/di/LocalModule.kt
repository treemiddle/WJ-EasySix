package com.example.local.di

import android.content.Context
import androidx.room.Room
import com.example.local.dao.LabelDao
import com.example.local.database.WJDatabase
import com.example.local.prefs.PrefsHelper
import com.example.local.prefs.PrefsHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext applicationContext: Context
    ): PrefsHelper {
        return PrefsHelperImpl(applicationContext)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): WJDatabase {
        return Room.databaseBuilder(
            applicationContext,
            WJDatabase::class.java, "wj_database"
        )
            .build()
    }

    @Provides
    fun providLabelDao(database: WJDatabase): LabelDao {
        return database.labelDao()
    }

}