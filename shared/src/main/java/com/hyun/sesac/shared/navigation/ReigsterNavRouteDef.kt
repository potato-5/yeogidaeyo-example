package com.hyun.sesac.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RegisterNavigationRoute : YeogidaeyoNavigation {
    @Serializable
    data object RegisterTab : RegisterNavigationRoute
}