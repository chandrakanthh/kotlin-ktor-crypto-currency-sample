package com.example.mycrypto.di

import com.example.mycrypto.data.services.RemoteService
import com.example.mycrypto.data.services.RepositorySDK
import com.example.mycrypto.data.services.retrofit.RetroClient
import com.example.mycrypto.data.services.retrofit.RetroRepos
import com.example.mycrypto.data.services.retrofit.RetrofitApi
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

    @Provides
    @Singleton
    fun provideRetrofitService(): RetroClient = RetroClient()

    @Provides
    @Singleton
    fun provideRetroRepoSDK(retroClient: RetroClient) : RetroRepos =
        RetroRepos(retrofitService = retroClient)
}