package com.hyun.sesac.domain.usecase.auth

import com.hyun.sesac.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: UserRepository
){
    suspend operator fun invoke(){
        repository.clearUserInfo()
    }
}