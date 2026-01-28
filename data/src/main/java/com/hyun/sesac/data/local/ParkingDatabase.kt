package com.hyun.sesac.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hyun.sesac.data.dao.ParkingDao
import com.hyun.sesac.data.dao.UserDao
import com.hyun.sesac.data.entity.ParkingEntity
import com.hyun.sesac.data.entity.UserEntity

@Database(
    entities = [
        ParkingEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ParkingDatabase : RoomDatabase() {
    abstract fun parkingDao(): ParkingDao
    abstract fun userDao(): UserDao
}