package com.hyun.sesac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hyun.sesac.shared.ui.theme.BgWhite
import com.hyun.sesac.ui.screen.EntryScreen
import com.hyun.sesac.ui.theme.YeogidaeyoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO splash 화면 추가
        // TODO Hilt view model 선언 해주면 owner는 activity ( activity가 끝날 때 같이 끝남 )
        enableEdgeToEdge()
        setContent {
            // val globalViewModel: AppViewModel = hiltViewModel()
            // navhost 전에 호출
            YeogidaeyoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BgWhite
                ) {
                    EntryScreen()
                }
            }

            /*val keyHash = Utility.getKeyHash(this)
            Log.d("keyHash",keyHash)*/
        }
    }
}
