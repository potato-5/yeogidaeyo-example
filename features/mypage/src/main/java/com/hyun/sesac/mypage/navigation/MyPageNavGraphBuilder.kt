package com.hyun.sesac.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyun.sesac.mypage.ui.screen.MyPageScreen
import com.hyun.sesac.shared.navigation.MyPageNavigationRoute

fun NavGraphBuilder.myPageNavGraph(navController: NavController) {
    composable<MyPageNavigationRoute.MyPageTab>{
        MyPageScreen()
    }
}