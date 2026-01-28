package com.hyun.sesac.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TurnRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.theme.BodyText
import com.hyun.sesac.shared.ui.theme.CaptionText
import com.hyun.sesac.shared.ui.theme.ErrorRed
import com.hyun.sesac.shared.ui.theme.HeadingTitle
import com.hyun.sesac.shared.ui.theme.LightIndigo
import com.hyun.sesac.shared.ui.theme.PureWhite
import com.hyun.sesac.shared.ui.theme.SuccessGreen
import com.hyun.sesac.shared.ui.theme.SuccessLightGreen
import com.hyun.sesac.shared.ui.theme.PreviewTheme

@Composable
fun ParkingStatusSection(
    total: Int?,
    current: Int?,
    modifier: Modifier = Modifier,
    onRouteClick: () -> Unit
){
    Surface(
        color = SuccessLightGreen,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ){
        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column{
                Row(verticalAlignment = Alignment.CenterVertically){
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(SuccessGreen, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text =  stringResource(id = R.string.parking_available),
                        // TODO  text =  stringResource(id = R.string.parking_congested),
                        color = SuccessGreen,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                // 주차 대수 (54 / 101)
                Text(
                    buildAnnotatedString {
                        withStyle(style = MaterialTheme.typography.headlineSmall.toSpanStyle().copy(
                            color = HeadingTitle
                        )) {
                            append("$current")
                        }
                        withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                            color = CaptionText
                        )) {
                            append(" / ")
                        }
                        withStyle(style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                            color = CaptionText
                        )) {
                            append("$total")
                        }
                    }
                )
            }

            Surface(
                color = LightIndigo, // 보라색 (피그마 참고)
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .size(72.dp) // 정사각형 크기
                    .clickable {
                        onRouteClick()
                    },
                shadowElevation = 4.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.TurnRight, // 화살표 아이콘
                        contentDescription = stringResource(id = R.string.find_route),
                        tint = PureWhite,
                        modifier = Modifier
                            .size(34.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.find_route),
                        style = MaterialTheme.typography.bodySmall,
                        color = PureWhite,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingStatusSectionPreview() {
    PreviewTheme {
        ParkingStatusSection(
            total = 101,
            current = 54,
            onRouteClick = {}
        )
    }
}
