package com.mimo.android.core.di

import com.mimo.android.data.repository.DataStoreRepository
import com.mimo.android.data.repository.TagRepository
import com.mimo.android.data.repository.UserRepository
import com.mimo.android.data.repositoryimpl.DataStoreRepositoryImpl
import com.mimo.android.data.repositoryimpl.TagRepositoryImpl
import com.mimo.android.data.repositoryimpl.UserRepositoryImpl
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
    fun provideTagRepository(
        tagRepositoryImpl: TagRepositoryImpl,
    ): TagRepository
}
