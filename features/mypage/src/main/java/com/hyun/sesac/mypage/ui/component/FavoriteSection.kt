package com.hyun.sesac.mypage.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.component.CommonWrapperCard

@Composable
fun FavoriteSection(
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit,
) {
    CommonWrapperCard(
        modifier = modifier.padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { onFavoriteClick() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                modifier = Modifier.size(80.dp),
                shadowElevation = 2.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "즐겨찾기",
                    tint = Color(0xFF6200EE),
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.favorite_parking_empty),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.favorite_empty_desc_1),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = stringResource(id = R.string.favorite_empty_desc_2),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun FavoritePreview() {
    MaterialTheme {
        Scaffold { innerPadding ->
            FavoriteSection(
                modifier = Modifier
                    .padding(innerPadding),
                onFavoriteClick = {}
            )
        }
    }
}