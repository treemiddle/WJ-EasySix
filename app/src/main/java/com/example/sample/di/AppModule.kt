package com.example.sample.di

import com.example.sample.base.AppNavigator
import com.example.sample.base.AppNavigatorImpl
import com.example.sample.ui.book.BookAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Singleton

@InstallIn(ActivityComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindMainNavigator(impl: AppNavigatorImpl): AppNavigator

}