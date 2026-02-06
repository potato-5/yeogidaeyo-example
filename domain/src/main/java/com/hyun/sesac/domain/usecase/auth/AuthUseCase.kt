package com.hyun.sesac.domain.usecase.auth

import javax.inject.Inject

data class AuthUseCase @Inject constructor(
    val loginGuest: LoginGuestUseCase,
    val logoutUseCase: LogoutUseCase,
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    //val loginKakao: LoginKakaoUseCase,
)