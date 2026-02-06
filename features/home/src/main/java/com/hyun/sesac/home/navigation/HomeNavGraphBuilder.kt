package com.hyun.sesac.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hyun.sesac.home.ui.screen.HomeScreen
import com.hyun.sesac.shared.navigation.HomeNavigationRoute

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    paddingValues: PaddingValues) {

    composable<HomeNavigationRoute.HomeTab>{
        HomeScreen(
            paddingValues = paddingValues,
            /*onNavigateToSearch = {
                navController.navigate(HomeNavigationRoute.SearchScreen)
            }*/
            /*navController.navigate(HomeNavigationRoute.HomeTab) {
                popUpTo(LoginNavigationRoute.LoginScreen) {
                    inclusive = true
                }
                launchSingleTop = true*/
        )
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