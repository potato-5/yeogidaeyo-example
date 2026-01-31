package com.hyun.sesac.register.ui.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterCameraScreen(
    paddingValues: PaddingValues,
    onClose: () -> Unit,
    onImageCaptured: (Uri) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // [핵심 변경] LifecycleCameraController 사용
    // 이것 하나로 Preview, ImageCapture, ImageAnalysis를 다 제어합니다.
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                LifecycleCameraController.IMAGE_CAPTURE or
                        LifecycleCameraController.IMAGE_ANALYSIS // 나중에 ML Kit OCR 붙일 때 필요!
            )
            bindToLifecycle(lifecycleOwner) // 라이프사이클 자동 관리
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // 후면 카메라
        }
    }

    Box(modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize()
        .background(Color.Black)) {
        // 1. 카메라 프리뷰
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    controller = cameraController
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // 2. 촬영 버튼
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp)
                .navigationBarsPadding()
        ) {
            Button(
                onClick = {
                    takePicture(context, cameraController, onImageCaptured)
                },
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                contentPadding = PaddingValues(0.dp)
            ) {
                // 내부 원
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, CircleShape)
                        .border(2.dp, Color.Black.copy(alpha = 0.2f), CircleShape)
                )
            }
        }

        // 3. 취소 버튼
        Button(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Text("취소")
        }
    }
}

// 사진 촬영 로직 분리 (참고 코드 스타일 반영)
private fun takePicture(
    context: Context,
    cameraController: LifecycleCameraController,
    onImageCaptured: (Uri) -> Unit
) {
    // 갤러리가 아닌 앱 캐시 폴더에 임시 저장 (주차 등록용이므로 갤러리 오염 방지)
    val photoFile = context.createImageFile()

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    cameraController.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                // 저장된 파일의 Uri 반환
                onImageCaptured(Uri.fromFile(photoFile))
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraX", "사진 촬영 실패", exception)
            }
        }
    )
}

// 파일 생성 유틸
private fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", cacheDir)
}