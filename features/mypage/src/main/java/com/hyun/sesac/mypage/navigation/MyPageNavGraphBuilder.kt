package com.hyun.sesac.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyun.sesac.mypage.ui.MyPageScreen
import com.hyun.sesac.shared.navigation.MyPageNavigationRoute

fun NavGraphBuilder.myPageNavGraph(navController: NavController, paddingValues: PaddingValues) {
    composable<MyPageNavigationRoute.MyPageTab>{
        MyPageScreen(paddingValues)
    }
}