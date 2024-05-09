package com.mimo.android.core.di

import com.mimo.android.data.datasource.local.LocalDataSource
import com.mimo.android.data.datasource.local.LocalDataSourceImpl
import com.mimo.android.data.datasource.remote.TagRemoteDataSource
import com.mimo.android.data.datasource.remote.TagRemoteDataSourceImpl
import com.mimo.android.data.datasource.remote.UserRemoteDataSource
import com.mimo.android.data.datasource.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {
    @Singleton
    @Binds
    fun provideLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl,
    ): LocalDataSource

    @Singleton
    @Binds
    fun provideUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl,
    ): UserRemoteDataSource

    @Singleton
    @Binds
    fun provideTagRemoteDataSource(
        tagRemoteDataSourceImpl: TagRemoteDataSourceImpl,
    ): TagRemoteDataSource
}
