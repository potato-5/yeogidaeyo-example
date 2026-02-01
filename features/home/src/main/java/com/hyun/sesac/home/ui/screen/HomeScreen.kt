package com.hyun.sesac.home.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hyun.sesac.home.ui.component.NavigationSection
import com.hyun.sesac.home.ui.component.ParkingSpotDetail
import com.hyun.sesac.home.ui.state.UiEvent
import com.hyun.sesac.home.viewmodel.CurrentLocationViewModel
import com.hyun.sesac.home.viewmodel.MapViewModel
import com.hyun.sesac.shared.ui.component.commonToast
import com.hyun.sesac.shared.ui.theme.PureWhite

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    locationViewModel: CurrentLocationViewModel = hiltViewModel(),
    parkingViewModel: MapViewModel = hiltViewModel(),
) {
    // 12/08 TODO BottomSheet Coroutine으로 main thread에서 실행 안되도록 ( 예제 찾아보면 잇음 )
    val context = LocalContext.current // Toast용 Context
    // bottom sheet state
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = false
        )
    )

    val uiState by parkingViewModel.uiState.collectAsStateWithLifecycle()
    // select spot
    val selectedSpot = uiState.selectedSpot
    // bookmark state
    val isBookmarked = selectedSpot?.isBookmarked ?: false

    LaunchedEffect(Unit) {
        parkingViewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowToast -> {
                    commonToast(context, event.message)
                }
            }
        }
    }

    // select find location
    var showNaviSheet by rememberSaveable { mutableStateOf(false) }

    // sheet peek 설정
    val bottomBarHeight = paddingValues.calculateBottomPadding()
    val peekHeight = if (selectedSpot != null) {
        bottomBarHeight + 200.dp
    } else {
        0.dp
    }

    LaunchedEffect(selectedSpot) {
        Log.d("DEBUG_UI", "UI 수신 데이터: ${selectedSpot?.name}, 개수: ${selectedSpot?.currentCnt}")
        if (selectedSpot == null) {
            sheetState.bottomSheetState.hide() // 데이터 없으면 숨기기
        } else {
            // 데이터가 새로 들어왔는데, 시트가 숨겨져 있다면 살짝 올리기
            if (sheetState.bottomSheetState.currentValue == SheetValue.Hidden) {
                sheetState.bottomSheetState.partialExpand()
            }
        }
    }

    // 다른 탭으로 갈 때 선택된 주차장 정보를 날려버림 -> 바텀시트 닫힘
    DisposableEffect(Unit) {
        onDispose {
            parkingViewModel.cleanSpot()
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = peekHeight,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = bottomBarHeight),
        sheetContent = {
            ParkingSpotDetail(
                selectedSpot = selectedSpot,
                isBookmarked = isBookmarked,
                onBookmarkClick = {
                    selectedSpot?.let { parking ->
                        parkingViewModel.toggleBookmark()
                    }
                },
                onRouteClick = {
                    showNaviSheet = true
                },
                currentCount = selectedSpot?.currentCnt ?: 0,
                totalCount = selectedSpot?.totalCnt ?: 0,
                modifier = Modifier
            )
            if (showNaviSheet) {
                NavigationSection(
                    onDismissRequest = {
                        showNaviSheet = false
                    },
                    onAppSelected = { appName ->
                        showNaviSheet = false
                        // TODO 실제 앱 실행 로직 넣으면 됨 (INTENT)
                        Log.d("$appName 실행", "$appName 실행")
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContainerColor = PureWhite,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            GoogleMapScreen(locationViewModel, parkingViewModel)

            // TODO search bar
            // TODO floating button 내 위치 버튼
        }
    }
}


