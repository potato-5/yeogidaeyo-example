package com.hyun.sesac.ui.component

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector
import com.hyun.sesac.R
import com.hyun.sesac.shared.navigation.HomeNavigationRoute
import com.hyun.sesac.shared.navigation.MyPageNavigationRoute
import com.hyun.sesac.shared.navigation.RegisterNavigationRoute
import com.hyun.sesac.shared.navigation.YeogidaeyoNavigation

data class BottomAppBarItem(
    val tabName: Int,
    val icon: ImageVector = Icons.Filled.Home,
    val destination: YeogidaeyoNavigation
    ){

    companion object{
        fun fetchBottomAppBarItems() = listOf(
            BottomAppBarItem(
                tabName = R.string.tab_home,
                icon = Icons.Filled.Home,
                destination = HomeNavigationRoute.HomeTab
            ),
            BottomAppBarItem(
                tabName = R.string.tab_register,
                icon = Icons.Filled.AddCircleOutline,
                destination = RegisterNavigationRoute.RegisterTab
            ),
            BottomAppBarItem(
                tabName = R.string.tab_mypage,
                icon = Icons.Filled.PersonOutline,
                destination = MyPageNavigationRoute.MyPageTab
            )
        )
    }
}