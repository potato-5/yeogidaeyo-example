package com.hyun.sesac.register.ui.screen

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.hyun.sesac.register.ui.component.InputSection
import com.hyun.sesac.register.ui.component.LocationSection
import com.hyun.sesac.register.ui.component.PhotoSection
import com.hyun.sesac.register.viewmodel.RegisterEvent
import com.hyun.sesac.register.viewmodel.RegisterViewModel // [Import 확인]
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.component.CommonButton
import com.hyun.sesac.shared.ui.component.CommonTitle
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RegisterScreen(
    paddingValues: PaddingValues,
    viewModel: RegisterViewModel,
    onCameraClick: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(true) {
        viewModel.event.collectLatest { event ->
            when(event){
                is RegisterEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    AnimatedContent(
        targetState = uiState.isLoading,
        label = "RegisterScreenTransition"
    ) { isLoading ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        } else {
            val isRegisterSaved = uiState.savedItem == null
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                CommonTitle(stringResource(id = R.string.my_parking_register_title))

                LocationSection(
                    locationName = uiState.locationFromGps.ifEmpty { stringResource(id = R.string.register_location_empty) },
                    parkingSpot = uiState.parkingSpot.ifEmpty { stringResource(id = R.string.register_spot_empty) },
                    modifier = Modifier
                )

                PhotoSection(
                    modifier = Modifier,
                    editEnabled = isRegisterSaved,
                    capturedImageUri = uiState.capturedImageUri?.toString(), // Uri -> String 변환
                    onTakePhotoClick = {
                        // 권한 확인 후 카메라 열기 시도
                        if (cameraPermissionState.status.isGranted) {
                            onCameraClick()
                        } else {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    }
                )

                InputSection(
                    floor = uiState.floor,
                    memo = uiState.memo,
                    editEnabled = isRegisterSaved,
                    modifier = Modifier,
                    onMemoChange = { viewModel.onMemoChanged(it) },
                    onFloorUp = { viewModel.onFloorLevelChanged(isUp = true) },
                    onFloorDown = { viewModel.onFloorLevelChanged(isUp = false) }
                )

                CommonButton(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = if (isRegisterSaved) stringResource(id = R.string.register_my_parking) else {
                        stringResource(id = R.string.delete_my_parking)
                    },
                    isRegister = isRegisterSaved,
                    onClick = {
                        if (isRegisterSaved) {
                            viewModel.saveRegister()
                        } else {
                            viewModel.deleteRegister()
                        }
                    }
                )
            }
        }
    }
}
