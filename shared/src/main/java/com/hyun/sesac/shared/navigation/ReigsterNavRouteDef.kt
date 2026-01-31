package com.hyun.sesac.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RegisterNavigationRoute : YeogidaeyoNavigation {
    // 2개의 연결되는 screen을 잇기 위한 공통 route
    @Serializable
    data object RegisterGraph : RegisterNavigationRoute

    @Serializable
    data object RegisterTab : RegisterNavigationRoute

    @Serializable
    data object RegisterPhotoScreen : RegisterNavigationRoute
}