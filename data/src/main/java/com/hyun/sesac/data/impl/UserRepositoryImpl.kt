package com.hyun.sesac.data.impl

import com.hyun.sesac.data.dao.UserDao
import com.hyun.sesac.data.entity.UserEntity
import com.hyun.sesac.data.mapper.toDomainUser
import com.hyun.sesac.domain.model.UserInfo
import com.hyun.sesac.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    // 현재 로그인 된 유저 정보 흐름 ( firebase 저장 시 )
    override fun currentUser(): Flow<UserInfo?> = userDao.getUser().map { entity ->
        entity?.toDomainUser()
    }

    // 게스트 가입
    override suspend fun saveUserInfo(userInfo: UserInfo) {
        val guestUser = UserEntity(
            id = userInfo.id,
            nickname = userInfo.nickname,
            carNum = userInfo.carNumber,
            provider = userInfo.provider
        )
        userDao.saveUser(guestUser)
    }

    override suspend fun clearUserInfo() {
        userDao.deleteUser()
    }

    // 나중에 카카오 로그인 추가 시 호출 될 함수
    override suspend fun upgradeToOAuth(id: String){
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