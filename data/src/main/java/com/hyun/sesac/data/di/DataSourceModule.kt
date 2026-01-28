package com.hyun.sesac.data.di

import com.hyun.sesac.data.datasource.ParkingDataSource
import com.hyun.sesac.data.remote.firebase.FirestoreParkingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindParkingDataSource(
        impl: FirestoreParkingDataSourceImpl
    ): ParkingDataSource
}