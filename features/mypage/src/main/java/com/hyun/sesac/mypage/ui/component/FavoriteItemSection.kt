package com.hyun.sesac.mypage.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.shared.ui.component.CommonIcon
import com.hyun.sesac.shared.ui.component.CommonWrapperCard
import com.hyun.sesac.shared.ui.theme.PreviewTheme

@Composable
fun FavoriteItemSection(
    modifier: Modifier = Modifier,
    item: Parking,
    onItemClick: () -> Unit = {}
){
    CommonWrapperCard(
        modifier = modifier.padding(top = 8.dp)
        // .clickable { onItemClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            CommonIcon(
                icon = Icons.Default.Favorite,
                iconPadding = 16.dp,
                iconColor = Color(0xFF6200EE),
                modifier = Modifier
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(4.dp))

                // 주소 표시 (옵션)
                /*Text(
                    text = item.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1 // 너무 길면 자르기
                )*/

                Spacer(modifier = Modifier.height(8.dp))

                // 주차 대수 표시 (옵션)
                Row {
                    Text(
                        text = "현재 ${item.currentCnt}대",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF6200EE),
                    )
                    Text(
                        text = " / 총 ${item.totalCnt}대",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun FavoriteItemPreview() {
    PreviewTheme {
        Scaffold { innerPadding ->
            FavoriteItemSection(
                modifier = Modifier
                    .padding(innerPadding),
                item = Parking(
                    "123",
                    "동대문 새싹 주차장",
                    "서울시 동대문구 청량리",
                    12.0,
                    12.0,
                    currentCnt = 30,
                    totalCnt = 100,
                    isBookmarked = true,
                    updatedTime = "12시 30분",
                )
            )
        }
    }
}