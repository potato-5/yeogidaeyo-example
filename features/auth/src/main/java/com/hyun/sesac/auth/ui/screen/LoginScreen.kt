package com.hyun.sesac.auth.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyun.sesac.auth.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit, // 로그인 성공 시 홈으로 이동하는 콜백
    viewModel: LoginViewModel = hiltViewModel()
) {
    val nickname by viewModel.nickname.collectAsStateWithLifecycle()
    val carNum by viewModel.carNum.collectAsStateWithLifecycle()

    // ViewModel의 성공 신호 감지
    LaunchedEffect(true) {
        viewModel.loginEvent.collectLatest {
            onLoginSuccess() // 네비게이션 이동 (Login -> Home)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "게스트 로그인", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // 닉네임 입력
        OutlinedTextField(
            value = nickname,
            onValueChange = viewModel::updateNickname,
            label = { Text("닉네임") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 차번호 입력
        OutlinedTextField(
            value = carNum,
            onValueChange = viewModel::updateCarNum,
            label = { Text("차량 번호 (예: 12가 3456)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 로그인 버튼
        Button(
            onClick = { viewModel.onGuestLoginClick() },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = nickname.isNotBlank() && carNum.isNotBlank() // 둘 다 입력해야 활성화
        ) {
            Text("시작하기")
        }
    }
}