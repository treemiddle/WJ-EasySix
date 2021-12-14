package com.example.sample.di

import com.example.sample.base.AppNavigator
import com.example.sample.base.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindMainNavigator(impl: AppNavigatorImpl): AppNavigator

}