package com.hyun.sesac.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface LoginNavigationRoute : YeogidaeyoNavigation {
    // TODO ROUTE 로 구분하는 방식은 옛날 방식 , 예제 방식 ( 과제 ) 보고 변경
    @Serializable
    data object LoginScreen : LoginNavigationRoute
}