package com.hyun.sesac.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyun.sesac.auth.ui.screen.LoginScreen
import com.hyun.sesac.shared.navigation.HomeNavigationRoute
import com.hyun.sesac.shared.navigation.LoginNavigationRoute

fun NavGraphBuilder.loginNavGraph(
    navController: NavController,
    paddingValues: PaddingValues,
) {
    composable<LoginNavigationRoute.LoginScreen> {
        LoginScreen(
            paddingValues = paddingValues,
            onLoginSuccess = {
                navController.navigate(HomeNavigationRoute.HomeTab) {
                    popUpTo(LoginNavigationRoute.LoginScreen) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            },
        )
        //paddingValues = paddingValues,
        /*onNavigateToSearch = {
            navController.navigate(HomeNavigationRoute.SearchScreen)
        }*/
    }
    /*    composable<HomeNavigationRoute.SearchScreen>{
            SearchScreen(
                onNavigateToDetail = { query ->
                    navController.navigate(HomeNavigationRoute.DetailScreen(query))
                },
                onBackClicked = { navController. popBackStack() }
            )
        }
        composable<HomeNavigationRoute.DetailScreen>{ backStackEntry ->
            DetailScreen(
                paddingValues = paddingValues,
                onBackClicked = { navController. popBackStack() }
            )
        }*/
}