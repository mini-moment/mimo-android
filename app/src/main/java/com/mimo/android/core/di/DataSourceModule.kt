package com.mimo.android.core.di

import com.mimo.android.data.datasource.local.LocalDataSource
import com.mimo.android.data.datasource.local.LocalDataSourceImpl
import com.mimo.android.data.datasource.remote.MapRemoteDataSource
import com.mimo.android.data.datasource.remote.MapRemoteDataSourceImpl
import com.mimo.android.data.datasource.remote.PostRemoteDataSource
import com.mimo.android.data.datasource.remote.PostRemoteDataSourceImpl
import com.mimo.android.data.datasource.remote.TagRemoteDataSource
import com.mimo.android.data.datasource.remote.TagRemoteDataSourceImpl
import com.mimo.android.data.datasource.remote.UserRemoteDataSource
import com.mimo.android.data.datasource.remote.UserRemoteDataSourceImpl
import com.mimo.android.data.datasource.remote.VideoRemoteDataSource
import com.mimo.android.data.datasource.remote.VideoRemoteDataSourceImpl
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
