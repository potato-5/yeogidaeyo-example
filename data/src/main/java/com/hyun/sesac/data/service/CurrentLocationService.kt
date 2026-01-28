package com.hyun.sesac.data.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.hyun.sesac.data.R
import com.hyun.sesac.data.di.CoroutinesModule.providesIoDispatcher
import com.hyun.sesac.domain.repository.CurrentLocationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.Dispatcher
import javax.inject.Inject

const val NOTIFICATION_ID = 12345

// TODO 12/22 service의 위치는 compose랑 같은 presentation 단위 ( 같은 곳 위치 )
// TODO 12/22 screen -> service -> viewmodel -> usecase -> repository -> impl
// TODO 12/22 impl에서 call back flow 주면 됨 why? -> data source 자체가 gps 이기 떄문

@AndroidEntryPoint
class CurrentLocationService : Service() {
    @Inject
    lateinit var locationRepository: CurrentLocationRepository
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 포그라운드 알림 시작
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundNotify()
        }
        Log.e("TAG", "Service Start")
        locationRepository.getCurrentLocationUpdates()
            .onEach { location ->
                /**
                 * 필요하다면 여기서 추가작업(클라우드 전송, Room DB 기록, File 누적 등)
                 */
                Log.d("TAG", "새로운 위치 수신: ${location.latitude}, ${location.longitude}")
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(serviceScope)

        return START_STICKY
    }

    private lateinit var notificationManager: NotificationManager

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundNotify() {
        val channelId = "location_notification_channel"
        notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Current Location Service",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("사용자 위치를 10초마다 추적 중")
            .setContentText("앱이 현재 위치를 추적하고 있습니다.")
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            /**
             * [사용자가 실수로 끄면 안 되는 중요한 작업에 대한 알림에 사용]
             * finish : stopForeground(true), NotificationManager.cancel(notificationId)
             * ex::
             * 위치 추적 앱: 현재 위치를 계속 추적 중일 때             *
             * 음악 플레이어: 음악이 재생 중일 때             *
             * 전화 통화: 통화가 진행 중일 때             *
             * 파일 다운로드: 파일 다운로드가 진행 중일 때
             */
            .setOngoing(true)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()

    }

    override fun onBind(intent: Intent?): IBinder? = null
}