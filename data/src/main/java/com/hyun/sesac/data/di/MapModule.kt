package com.hyun.sesac.data.di

import com.hyun.sesac.data.impl.CurrentLocationRepositoryImpl
import com.hyun.sesac.domain.repository.CurrentLocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// TODO 12/22 각 모듈 분리 ( service, repository, data source 이렇게 )
@Module
@InstallIn(SingletonComponent::class)
abstract class MapModule {
    @Binds
    @Singleton
    abstract fun provideCurrentLocationRepository(
        impl: CurrentLocationRepositoryImpl
    ): CurrentLocationRepository

    // TODO 12/22 usecase는 여기 있으면 안됨
    // TODO 12/22 repository 따로 분리하기 srp 원칙 -> return 값이 두개면 안됨
    // TODO 12/22 느슨한 결합으로 해야 됨 / usecase는 inject 로 결합
}