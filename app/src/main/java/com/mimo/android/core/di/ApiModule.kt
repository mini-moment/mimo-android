package com.mimo.android.core.di

import com.mimo.android.data.network.api.MapApi
import com.mimo.android.data.network.api.PostApi
import com.mimo.android.data.network.api.TagApi
import com.mimo.android.data.network.api.UserApi
import com.mimo.android.data.network.api.VideoApi
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
        retrofit: Retrofit
    ) : MapApi = retrofit.create()
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
