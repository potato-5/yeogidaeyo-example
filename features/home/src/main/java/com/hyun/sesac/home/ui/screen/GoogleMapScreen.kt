package com.hyun.sesac.home.ui.screen

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hyun.sesac.domain.result.ProductResult
import com.hyun.sesac.home.viewmodel.CurrentLocationViewModel
import com.hyun.sesac.home.viewmodel.MapViewModel
import com.hyun.sesac.shared.ui.component.commonToast

@Composable
fun GoogleMapScreen(
    locationViewModel: CurrentLocationViewModel = hiltViewModel(),
    parkingViewModel: MapViewModel = hiltViewModel(),
) {
    val ctx = LocalContext.current

    // 서비스 시작을 위한 단일 함수
    fun startLocationService() {
        locationViewModel.startTracking()
    }

    var isLocationPermissionGranted by remember { mutableStateOf(false) }

    // GPS 설정 요청 결과 처리
    val settingResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            // GPS가 on 이므로 서비스 시작
            startLocationService()
        } else {
            commonToast(ctx, "GPS 활성화가 필요합니다")
        }
    }

    // GPS 설정 확인 및 서비스 시작을 통합한 함수
    fun checkGpsAndStart() {
        checkPhoneGpsSettings(
            context = ctx,
            onGpsEnabled = {
                // GPS가 이미 켜져 있으면 서비스 시작
                startLocationService()
            },
            onGpsSettingResolve = { intentSenderRequest ->
                // GPS를 켜야 하는 경우, 다이얼로그 요청
                settingResultLauncher.launch(intentSenderRequest)
            }
        )
    }

    // 권한 요청 결과 처리
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val fineLocation = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false )
            // 위치 권한이 부여되었는지 확인
            val coarseLocation = permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
            // 권한이 부여되었으면 GPS 확인 및 서비스 시작 절차 진행
            if(fineLocation || coarseLocation){
                isLocationPermissionGranted = true
                checkGpsAndStart()
            } else {
                commonToast(ctx, "위치 권한이 필요합니다")
            }
        }
    )

    // 화면이 처음 렌더링될 때 권한 상태 확인 및 요청
    LaunchedEffect(Unit) {
        val locationPermissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Android 13(Tiramisu) 이상 알림 권한 추가
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            locationPermissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        //현재 앱이 권한들을 가지고 있는지 확인
        val hasLocationPermission = locationPermissions.all {
            ContextCompat.checkSelfPermission(ctx, it) == PackageManager.PERMISSION_GRANTED
        }

        if (hasLocationPermission) {
            // 이미 권한이 있으면 GPS 확인 및 서비스 시작
            isLocationPermissionGranted = true
            checkGpsAndStart()
        } else {
            permissionLauncher.launch(locationPermissions.toTypedArray())
        }
    }

    //현재 컴포즈 스크린 stop 시 서비스 중지
    DisposableEffect(Unit) {
        onDispose {
            locationViewModel.stopTracking()
        }
    }

    // uiState 전체 구독
    val uiState by parkingViewModel.uiState.collectAsStateWithLifecycle()
    // TODO 초기 기본 값 서울 시청
    val seoulCityHall = LatLng(37.566535, 126.9779692)
    // TODO 현재 위치 ( 데이터 용 )
    val currentLocationResult by locationViewModel.currentLocation.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seoulCityHall, 15f)
    }
    val markerState = remember { MarkerState(position = seoulCityHall) }

    // camera 이동 + 위치 반영
    LaunchedEffect(currentLocationResult) {
        if (currentLocationResult is ProductResult.Success) {
            // 진짜 데이터 꺼내기 (.resultData)
            val newLocation = (currentLocationResult as ProductResult.Success).resultData

            val latLng = LatLng(newLocation.latitude, newLocation.longitude)
            markerState.position = latLng
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(latLng, 15f)
                )
            )
        }
    }

    // TODO 내 위치 표시 속성 ( 파란점 )
    val mapProperties = remember(isLocationPermissionGranted) {
        MapProperties(
            isMyLocationEnabled = isLocationPermissionGranted
        )
    }
    val mapUiSettings = remember {
        MapUiSettings(
            myLocationButtonEnabled = true
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = mapUiSettings,
        onMapClick = { latLng ->
            parkingViewModel.cleanSpot()
        }
    ) {
        // TODO MARKER 말고 내 위치 수정
        /* currentLocation?.let {
            Marker(
                state = MarkerState(LatLng(it.latitude, it.longitude)),
                title = "내 위치"
            )
        }*/
        uiState.parkingSpots.forEach { spot ->
            Marker(
                state = MarkerState(LatLng(spot.latitude, spot.longitude)),
                title = spot.name,
                // [참고] 아이콘 색상 변경 등이 필요하면 spot.isBookmarked 사용 가능
                onClick = { marker ->
                    parkingViewModel.onSpotSelected(spot)
                    false
                }
            )
        }
        /*parkingSpots.forEach { spot ->
            Marker(
                state = MarkerState(LatLng(spot.latitude, spot.longitude)),
                title = spot.name,
                //snippet = spot.priceInfo
                onClick = { marker ->
                    parkingViewModel.onSpotSelected(spot)
                    false
                }
            )
        }*/
    }
}

fun checkPhoneGpsSettings(
    context: Context,
    onGpsEnabled: () -> Unit,
    onGpsSettingResolve: (IntentSenderRequest) -> Unit
) {
    //위치 요청 설정 (정확도, 간격 등)
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

    //현재 단말기의 GPS 설정이 위치 요청을 만족하는지 확인하기 위한 빌더
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val client: SettingsClient = LocationServices.getSettingsClient(context)

    //설정 확인 요청 실행
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

    //성공 리스너 (GPS가 켜져 있음)
    task.addOnSuccessListener {
        // GPS가 켜져 있으므로 성공 콜백 호출
        onGpsEnabled()
    }

    //실패 리스너 (GPS가 꺼져 있음)
    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            // 사용자가 직접 GPS 설정을 변경할 수 있는 경우
            try {
                // GPS 활성화 다이얼로그를 띄우기 위한 요청 생성 후 콜백 호출
                val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                onGpsSettingResolve(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // 다이얼로그를 보여줄 수 없는 예외적인 경우, 시스템 설정 화면으로 직접 이동하여 사용자가 직접 on 설정유도
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
    }
}