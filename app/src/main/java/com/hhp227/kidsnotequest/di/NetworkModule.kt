package com.hhp227.kidsnotequest.di

import com.hhp227.kidsnotequest.api.MainService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideMainService() = MainService.create()
}