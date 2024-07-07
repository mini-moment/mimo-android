package com.mimo.android.core.di

import com.mimo.android.data.api.MapApi
import com.mimo.android.data.api.PostApi
import com.mimo.android.data.api.TagApi
import com.mimo.android.data.api.UserApi
import com.mimo.android.data.api.VideoApi
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
    fun provideUserApi(
        retrofit: Retrofit,
    ): UserApi = retrofit.create()

    @Provides
    @Singleton
    fun provideMapApi(
        retrofit: Retrofit,
    ): MapApi = retrofit.create()

    @Provides
    @Singleton
    fun provideTagApi(
        retrofit: Retrofit,
    ): TagApi = retrofit.create()

    @Provides
    @Singleton
    fun provideVideoApi(
        retrofit: Retrofit,
    ): VideoApi = retrofit.create()

    @Provides
    @Singleton
    fun providePostApi(
        retrofit: Retrofit,
    ): PostApi = retrofit.create()
}
