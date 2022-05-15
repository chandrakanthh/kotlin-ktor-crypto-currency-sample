package com.example.mycrypto.di

import com.example.mycrypto.data.services.RemoteService
import com.example.mycrypto.data.services.RepositorySDK
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteServices(): RemoteService = RemoteService()

    @Provides
    @Singleton
    fun provideRepositorySDK(remoteService: RemoteService): RepositorySDK =
    RepositorySDK(remoteService = remoteService)
}