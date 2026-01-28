package com.hyun.sesac.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyun.sesac.register.ui.RegisterScreen
import com.hyun.sesac.shared.navigation.RegisterNavigationRoute

fun NavGraphBuilder.registerNavGraph(navController: NavController, paddingValues: PaddingValues) {
    composable<RegisterNavigationRoute.RegisterTab>{
        RegisterScreen(
            paddingValues = paddingValues
        )
    }
}