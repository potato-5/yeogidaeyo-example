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
import com.hyun.sesac.domain.model.KakaoSearchModel
import com.hyun.sesac.domain.model.RegisterParkingModel
import com.hyun.sesac.domain.repository.KakaoApiRepository
import com.hyun.sesac.domain.repository.RegisterRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KakaoApiRepositoryImpl @Inject constructor(
    private val api: KakaoApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : KakaoApiRepository {

    // 카카오 api로 분리 필요
    override suspend fun getBuildingName(lat: Double, lng: Double): ProductResult<String?> {
        val apiKey = "KakaoAK ${BuildConfig.KAKAO_API_KEY}"
        return withContext(ioDispatcher) {
            safeProductResultCall {
                api.coord2Address(apiKey, lng.toString(), lat.toString())
                    .toBestAddressString()
            }
        }
    }

    override suspend fun searchPlace(
        query: String,
        lat: Double?,
        lng: Double?,
    ): ProductResult<List<KakaoSearchModel>> {
        val apiKey = "KakaoAK ${BuildConfig.KAKAO_API_KEY}"
        return withContext(ioDispatcher){
            safeProductResultCall {
                val response = api.searchKeyword(
                    apiKey = apiKey,
                    query = query,
                    longitude = lng?.toString(),
                    latitude = lat?.toString()
                )
                response.documents.map { it.toDomain() }
            }
        }
    }
}
