package com.hyun.sesac.data.di

import com.hyun.sesac.data.impl.BookmarkRepositoryImpl
import com.hyun.sesac.data.remote.firebase.FirestoreParkingRepositoryImpl
import com.hyun.sesac.data.impl.UserRepositoryImpl
import com.hyun.sesac.domain.repository.BookmarkRepository
import com.hyun.sesac.domain.repository.ParkingRepository
import com.hyun.sesac.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindParkingRepository(
        impl: FirestoreParkingRepositoryImpl
    ): ParkingRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(
        bookmarkRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository
}