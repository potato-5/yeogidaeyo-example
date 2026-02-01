package com.hyun.sesac.data.impl

import com.hyun.sesac.data.dao.UserDao
import com.hyun.sesac.data.entity.UserEntity
import com.hyun.sesac.data.impl.utils.asProductResult
import com.hyun.sesac.data.impl.utils.safeProductResultCall
import com.hyun.sesac.data.mapper.toDomainUser
import com.hyun.sesac.data.mapper.toUserEntity
import com.hyun.sesac.domain.model.UserInfo
import com.hyun.sesac.domain.repository.UserRepository
import com.hyun.sesac.domain.result.ProductResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
) : UserRepository {

    // 현재 로그인 된 유저 정보 흐름 ( firebase 저장 시 )
    override fun currentUser(): Flow<ProductResult<UserInfo?>> {
        return userDao.getUser()
            .map { it?.toDomainUser() }
            .asProductResult()
    }

    // 게스트 가입
    override suspend fun saveUserInfo(userInfo: UserInfo): ProductResult<Unit> {
        return safeProductResultCall {
            userDao.saveUser(userInfo.toUserEntity())
        }
    }

    override suspend fun clearUserInfo(): ProductResult<Unit> {
        return safeProductResultCall {
            userDao.deleteUser()
        }
    }

    // 나중에 카카오 로그인 추가 시 호출 될 함수
    override suspend fun upgradeToOAuth(id: String): ProductResult<Unit> {
        return safeProductResultCall {
            val guestUser = userDao.getUser().first()
            val savedCarNum = guestUser?.carNum ?: ""
            val savedNickname = guestUser?.nickname ?: ""

            val kakaoUser = UserEntity(
                id = id,
                nickname = savedNickname,
                carNum = savedCarNum,
                provider = "KAKAO"
            )

            userDao.deleteUser()
            userDao.saveUser(kakaoUser)
        }
    }
}