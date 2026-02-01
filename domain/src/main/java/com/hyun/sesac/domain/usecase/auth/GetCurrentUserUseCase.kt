package com.hyun.sesac.domain.usecase.auth

import com.hyun.sesac.domain.model.UserInfo
import com.hyun.sesac.domain.repository.UserRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: UserRepository
){
    operator fun invoke(): Flow<ProductResult<UserInfo?>> {
        return repository.currentUser()
    }
}