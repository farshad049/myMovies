package com.example.mystore.redux

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationStateModule {

    @Provides
    @Singleton
    fun providesApplicationStateStore():Store<ApplicationState>{
        return Store(ApplicationState())
    }
}