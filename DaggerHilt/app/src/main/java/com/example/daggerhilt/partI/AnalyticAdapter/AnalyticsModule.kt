package com.example.daggerhilt.partI.AnalyticAdapter

import com.example.daggerhilt.partI.AnalyticAdapter.inter.AnalyticsService
import com.example.daggerhilt.partI.AnalyticAdapter.inter.FirebaseAnalyticsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
    @Provides
    fun provideAnalyticsService(): AnalyticsService{
        return FirebaseAnalyticsService()
    }
}