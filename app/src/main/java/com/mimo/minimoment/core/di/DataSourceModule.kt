package com.mimo.minimoment.core.di

import com.mimo.data.datasource.local.LocalDataSource
import com.mimo.data.datasource.local.LocalDataSourceImpl
import com.mimo.data.datasource.remote.MapRemoteDataSource
import com.mimo.data.datasource.remote.MapRemoteDataSourceImpl
import com.mimo.data.datasource.remote.PostRemoteDataSource
import com.mimo.data.datasource.remote.PostRemoteDataSourceImpl
import com.mimo.data.datasource.remote.TagRemoteDataSource
import com.mimo.data.datasource.remote.TagRemoteDataSourceImpl
import com.mimo.data.datasource.remote.UserRemoteDataSource
import com.mimo.data.datasource.remote.UserRemoteDataSourceImpl
import com.mimo.data.datasource.remote.VideoRemoteDataSource
import com.mimo.data.datasource.remote.VideoRemoteDataSourceImpl
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
    fun provideMapRemoteDataSource(
        mapRemoteDataSourceImpl: MapRemoteDataSourceImpl
    ): MapRemoteDataSource

    @Singleton
    @Binds
    fun provideTagRemoteDataSource(
        tagRemoteDataSourceImpl: TagRemoteDataSourceImpl,
    ): TagRemoteDataSource

    @Singleton
    @Binds
    fun provideVideoRemoteDataSource(
        videoRemoteDataSourceImpl: VideoRemoteDataSourceImpl,
    ): VideoRemoteDataSource

    @Singleton
    @Binds
    fun providePostRemoteDataSource(
        postRemoteDataSourceImpl: PostRemoteDataSourceImpl
    ): PostRemoteDataSource
}
