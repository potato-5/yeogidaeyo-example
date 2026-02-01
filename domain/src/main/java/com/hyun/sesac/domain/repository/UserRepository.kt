package com.hyun.sesac.domain.repository

import com.hyun.sesac.domain.model.UserInfo
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    // 저장된 유저 정보를 가져옴 (Flow로 실시간 감지)
    fun currentUser(): Flow<ProductResult<UserInfo?>>

    // 유저 정보 저장
    suspend fun saveUserInfo(userInfo: UserInfo): ProductResult<Unit>

    // 초기화
    suspend fun clearUserInfo(): ProductResult<Unit>

    // 카카오 로그인 추가 시 호출 될 함수
    suspend fun upgradeToOAuth(id: String): ProductResult<Unit>
}