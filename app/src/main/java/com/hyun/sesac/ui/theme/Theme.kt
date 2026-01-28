package com.hyun.sesac.ui.theme


/*
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40


Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),


)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}*/

import com.hyun.sesac.shared.ui.theme.BgWhite
import com.hyun.sesac.shared.ui.theme.ErrorRed
import com.hyun.sesac.shared.ui.theme.HeadingTitle
import com.hyun.sesac.shared.ui.theme.LightIndigo
import com.hyun.sesac.shared.ui.theme.MainIndigo
import com.hyun.sesac.shared.ui.theme.NeutralGray
import com.hyun.sesac.shared.ui.theme.PureWhite
import com.hyun.sesac.shared.ui.theme.SuccessGreen
import com.hyun.sesac.shared.ui.theme.Typography
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MainIndigo,
    secondary = LightIndigo,
    tertiary = SuccessGreen
)

private val LightColorScheme = lightColorScheme(
    // [Brand Color]
    primary = MainIndigo,        // 주요 버튼, 활성화된 탭
    onPrimary = PureWhite,       // Primary 색상 위의 글자색 (흰색)

    // [Secondary]
    secondary = LightIndigo,     // 보조 버튼, FAB 등
    onSecondary = PureWhite,

    // [Tertiary / Status]
    tertiary = SuccessGreen,     // 긍정/성공 상태 표시용 (임의 지정)

    // [Background & Surface]
    background = BgWhite,        // 앱 전체 배경색 (약간 회색빛 도는 흰색)
    onBackground = HeadingTitle, // 배경 위의 글자색 (진한 남색)

    surface = PureWhite,         // 카드(Card), 시트(Sheet)의 배경색 (완전 흰색)
    onSurface = HeadingTitle,    // 카드 위의 글자색

    // [Error]
    error = ErrorRed,            // 에러 색상
    onError = PureWhite,         // 에러 바탕 위의 글자색

    // [Outline / Divider]
    outline = NeutralGray        // 테두리, 구분선, 비활성 아이콘

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun YeogidaeyoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}