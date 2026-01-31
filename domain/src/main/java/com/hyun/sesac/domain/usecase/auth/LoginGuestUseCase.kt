package com.hyun.sesac.domain.usecase.auth

import com.hyun.sesac.domain.model.UserInfo
import com.hyun.sesac.domain.repository.UserRepository
import java.util.UUID
import javax.inject.Inject

class LoginGuestUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        nickname: String,
        carNumber: String,
        provider: String
    ) {
        val userInfoModel = UserInfo(
            id = UUID.randomUUID().toString(),
            nickname = nickname,
            carNumber = carNumber,
            provider = provider
        )

        repository.saveUserInfo(userInfoModel)
    }
}
