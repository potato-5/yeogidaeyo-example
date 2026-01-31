package com.hyun.sesac.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.hyun.sesac.register.ui.screen.RegisterCameraScreen
import com.hyun.sesac.register.ui.screen.RegisterScreen
import com.hyun.sesac.register.viewmodel.RegisterViewModel
import com.hyun.sesac.shared.navigation.RegisterNavigationRoute

fun NavGraphBuilder.registerNavGraph(navController: NavController, paddingValues: PaddingValues) {
    // 1. Graph로 감싸서 ViewModel의 생명주기를 이 Graph가 살아있는 동안 유지시킵니다.
    navigation<RegisterNavigationRoute.RegisterGraph>(
        startDestination = RegisterNavigationRoute.RegisterTab
    ) {
        // [1] 주차 등록 화면
        composable<RegisterNavigationRoute.RegisterTab> { backStackEntry ->
            // 중요: Graph(부모)의 ViewModel을 가져옵니다.
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegisterNavigationRoute.RegisterGraph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            RegisterScreen(
                paddingValues = paddingValues,
                viewModel = viewModel,
                // 카메라 화면으로 이동하는 네비게이션 동작 전달
                onCameraClick = {
                    navController.navigate(RegisterNavigationRoute.RegisterPhotoScreen)
                }
            )
        }

        // [2] 카메라 화면 (BottomBar가 없는 전체 화면)
        composable<RegisterNavigationRoute.RegisterPhotoScreen> { backStackEntry ->
            // 중요: 똑같이 Graph(부모)의 ViewModel을 가져옵니다. (데이터 공유됨)
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegisterNavigationRoute.RegisterGraph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            RegisterCameraScreen(
                paddingValues = paddingValues,
                onClose = { navController.popBackStack() }, // 뒤로 가기
                onImageCaptured = { uri ->
                    viewModel.onImageCaptured(uri) // 데이터 저장
                    navController.popBackStack() // 저장 후 복귀
                }
            )
        }
    }
}
/*
fun NavGraphBuilder.registerNavGraph(navController: NavController, paddingValues: PaddingValues) {
    composable<RegisterNavigationRoute.RegisterTab>{
        RegisterScreen(
            paddingValues = paddingValues
        )
    }
    composable<RegisterNavigationRoute.RegisterPhotoScreen>{
        RegisterCameraScreen(
            paddingValues = paddingValues,
            onClose = { navController.popBackStack() },
            onImageCaptured = { uri ->
                navController.popBackStack()
            },
        )
    }
}*/
