package com.hyun.sesac.domain.usecase.auth

import com.hyun.sesac.domain.model.UserInfo
import com.hyun.sesac.domain.repository.UserRepository
import com.hyun.sesac.domain.result.ProductResult
import java.util.UUID
import javax.inject.Inject

class LoginGuestUseCase @Inject constructor(
    private val repository: UserRepository
) {
    // 반환 타입을 명시해주는 게 좋습니다.
    suspend operator fun invoke(
        nickname: String,
        carNumber: String,
        provider: String
    ): ProductResult<Unit> { // [변경] 결과 상자 반환

        // 1. 도메인 로직: 게스트 유저 모델 생성 (UUID 생성은 여기서 하는 게 맞습니다!)
        val userInfoModel = UserInfo(
            id = UUID.randomUUID().toString(),
            nickname = nickname,
            carNumber = carNumber,
            provider = provider
        )

        // 2. 저장 요청 후 결과 반환
        return repository.saveUserInfo(userInfoModel)
    }
}
