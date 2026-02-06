package com.hyun.sesac.data.di

import com.hyun.sesac.data.impl.KakaoApiRepositoryImpl
import com.hyun.sesac.data.impl.RoomParkingRepositoryImpl
import com.hyun.sesac.data.impl.RecognitionRepositoryImpl
import com.hyun.sesac.data.impl.RegisterRepositoryImpl
import com.hyun.sesac.data.remote.firebase.FirestoreParkingRepositoryImpl
import com.hyun.sesac.data.impl.UserRepositoryImpl
import com.hyun.sesac.domain.repository.RoomParkingRepository
import com.hyun.sesac.domain.repository.FireStoreParkingRepository
import com.hyun.sesac.domain.repository.KakaoApiRepository
import com.hyun.sesac.domain.repository.RecognitionRepository
import com.hyun.sesac.domain.repository.RegisterRepository
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
    ): FireStoreParkingRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindRoomParkingRepository(
        roomRepositoryImpl: RoomParkingRepositoryImpl
    ): RoomParkingRepository

    @Binds
    @Singleton
    abstract fun bindRecognitionRepository(
        recognitionRepositoryImpl: RecognitionRepositoryImpl
    ): RecognitionRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

    @Binds
    @Singleton
    abstract fun bindKakaoRepository(
        kakaoApiRepositoryImpl: KakaoApiRepositoryImpl
    ): KakaoApiRepository
}