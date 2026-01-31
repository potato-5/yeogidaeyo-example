package com.hyun.sesac.data.di

import android.content.Context
import androidx.room.Room
import com.hyun.sesac.data.dao.ParkingDao
import com.hyun.sesac.data.dao.RegisterDao
import com.hyun.sesac.data.dao.UserDao
import com.hyun.sesac.data.local.ParkingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideParkingDatabase(@ApplicationContext context: Context): ParkingDatabase {
        return Room.databaseBuilder(
            context,
            ParkingDatabase::class.java,
            "parking_db"
        )
            .fallbackToDestructiveMigration(true) // 개발 중에 DB 구조 바꾸면 초기화
            .build()
    }

    @Provides
    @Singleton
    fun provideParkingDao(database: ParkingDatabase): ParkingDao {
        return database.parkingDao()
    }

    @Provides
    @Singleton
    fun providerUserDao(database: ParkingDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun providerRegisterDao(database: ParkingDatabase): RegisterDao {
        return database.registerDao()
    }
}