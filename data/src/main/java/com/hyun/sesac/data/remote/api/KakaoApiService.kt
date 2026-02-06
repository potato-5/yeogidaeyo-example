package com.hyun.sesac.data.remote.api

import com.hyun.sesac.data.remote.dto.KakaoSearchResponse
import com.hyun.sesac.domain.model.KakaoApiModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {
    // [기존] 키워드 검색
    @GET("v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("x") longitude: String? = null,
        @Query("y") latitude: String? = null,
        @Query("radius") radius: Int = 20000,
        @Query("sort") sort: String = "accuracy"
    ): KakaoSearchResponse

    // [신규 추가] 좌표 -> 주소/건물명 변환
    @GET("v2/local/geo/coord2address.json")
    suspend fun coord2Address(
        @Header("Authorization") apiKey: String,
        @Query("x") longitude: String, // 경도
        @Query("y") latitude: String   // 위도
    ): KakaoApiModel
}
