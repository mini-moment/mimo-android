package com.mimo.android.core.di

import com.mimo.android.data.network.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideLoginApi(
        retrofit: Retrofit,
    ): LoginApi = retrofit.create()
}
