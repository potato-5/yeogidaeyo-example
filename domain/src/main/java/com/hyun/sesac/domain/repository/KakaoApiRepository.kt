package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.KakaoSearchModel
import com.hyun.sesac.domain.result.ProductResult

interface KakaoApiRepository {
    // 카카오 api로 분리 필요
    suspend fun getBuildingName(lat: Double, lng: Double): ProductResult<String?>

    suspend fun searchPlace(
        query: String,
        lat: Double? = null,
        lng: Double? = null
    ): ProductResult<List<KakaoSearchModel>>
}