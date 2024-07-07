package com.mimo.android.core.di

import com.mimo.android.data.repositoryimpl.DataStoreRepositoryImpl
import com.mimo.android.data.repositoryimpl.MapRepositoryImpl
import com.mimo.android.data.repositoryimpl.PostRepositoryImpl
import com.mimo.android.data.repositoryimpl.TagRepositoryImpl
import com.mimo.android.data.repositoryimpl.UserRepositoryImpl
import com.mimo.android.data.repositoryimpl.VideoRepositoryImpl
import com.mimo.android.domain.repository.DataStoreRepository
import com.mimo.android.domain.repository.MapRepository
import com.mimo.android.domain.repository.PostRepository
import com.mimo.android.domain.repository.TagRepository
import com.mimo.android.domain.repository.UserRepository
import com.mimo.android.domain.repository.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun provideDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl,
    ): DataStoreRepository

    @Singleton
    @Binds
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Singleton
    @Binds
    fun provideMapRepository(
        mapRepositoryImpl: MapRepositoryImpl,
    ): MapRepository

    @Singleton
    @Binds
    fun provideTagRepository(
        tagRepositoryImpl: TagRepositoryImpl,
    ): TagRepository

    @Singleton
    @Binds
    fun provideVideoRepository(
        videoRepositoryImpl: VideoRepositoryImpl,
    ): VideoRepository

    @Singleton
    @Binds
    fun providePostRepository(
        postRepositoryImpl: PostRepositoryImpl,
    ): PostRepository
}
