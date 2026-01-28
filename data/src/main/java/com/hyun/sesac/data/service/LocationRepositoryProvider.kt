package com.hyun.sesac.data.service

import android.content.Context
import com.hyun.sesac.data.impl.CurrentLocationRepositoryImpl
import com.hyun.sesac.domain.repository.CurrentLocationRepository

object LocationRepositoryProvider {

    @Volatile
    private var instance: CurrentLocationRepository? = null

    fun getInstance(context: Context): CurrentLocationRepository {
        return instance ?: synchronized(this) {
            val newInstance = CurrentLocationRepositoryImpl(context.applicationContext)
            instance = newInstance
            newInstance
        }
    }
}