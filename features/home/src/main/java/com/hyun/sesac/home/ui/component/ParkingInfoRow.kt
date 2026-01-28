package com.hyun.sesac.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.theme.CaptionText
import com.hyun.sesac.shared.ui.theme.HeadingTitle
import com.hyun.sesac.shared.ui.theme.MainIndigo
import com.hyun.sesac.shared.ui.theme.PreviewTheme

@Composable
fun ParkingInfoRow(
    label: String,
    value: String,
    subLabel: String? = null,
    isFree: Boolean = false,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 왼쪽 라벨 (회색)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = HeadingTitle
        )

        Spacer(modifier = Modifier.width(10.dp))

        // 오른쪽 값 영역
        Row(verticalAlignment = Alignment.CenterVertically) {
            // 보조 라벨이 있을 때만 표시 (예: "무료", "1,000원")
            if (subLabel != null) {
                Text(
                    text = subLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = CaptionText,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                // TODO 색상 로직 적용
                color = if (isHighlight) MainIndigo else if (isFree) HeadingTitle else HeadingTitle,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingInfoRowPreview() {
    PreviewTheme {
        ParkingInfoRow(
            label = stringResource(id = R.string.test),
            value = stringResource(id = R.string.test),
            subLabel = stringResource(id = R.string.test),
            isFree = true,
            isHighlight = true
        )
    }
}

