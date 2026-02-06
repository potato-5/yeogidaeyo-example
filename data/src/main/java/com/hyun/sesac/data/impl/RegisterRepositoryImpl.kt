package com.hyun.sesac.data.impl

import com.hyun.sesac.data.BuildConfig
import com.hyun.sesac.data.dao.RegisterDao
import com.hyun.sesac.data.di.IoDispatcher
import com.hyun.sesac.data.entity.RegisterEntity
import com.hyun.sesac.data.impl.utils.asProductResult
import com.hyun.sesac.data.impl.utils.safeProductResultCall
import com.hyun.sesac.data.mapper.toBestAddressString
import com.hyun.sesac.data.mapper.toDomain
import com.hyun.sesac.data.mapper.toEntity
import com.hyun.sesac.data.remote.api.KakaoApiService
import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.repository.RegisterRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerDao: RegisterDao,
) : RegisterRepository {

    override suspend fun insertRegister(item: RegisterParkingModel): ProductResult<Unit> {
        return safeProductResultCall {
            registerDao.insertRegister(item.toEntity())
        }
    }

    override suspend fun deleteRegister(item: RegisterParkingModel): ProductResult<Unit> {
        return safeProductResultCall {
            registerDao.deleteRegister(item.toEntity())
        }
    }

    override fun getRecentParking(): Flow<ProductResult<RegisterParkingModel?>> {
        return registerDao.getRecentParking()
            .map { entity -> entity?.toDomain() }
            .asProductResult()
        }
    }
