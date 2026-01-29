package com.hyun.sesac.mypage.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hyun.sesac.mypage.ui.component.FavoriteItemSection
import com.hyun.sesac.mypage.ui.component.FavoriteSection
import com.hyun.sesac.mypage.ui.component.ProfileSection
import com.hyun.sesac.mypage.viewmodel.MyPageViewModel
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.component.CommonTitle

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    //onNavigateToSearch: () -> Unit
) {
    val user by viewModel.userInfo.collectAsStateWithLifecycle()
    val favorites by viewModel.favoriteList.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    CommonTitle(stringResource(id = R.string.mypage_title))
                }

                IconButton(
                    onClick = { },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "설정",
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }

        item {
            user?.let { userInfo ->
                ProfileSection(
                    name = userInfo.nickname,
                    carNumber = userInfo.carNumber,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.favorite_parking_title),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        // TODO 여기 좀 버벅여서, 이거 수정하기
        if (favorites.isEmpty()) {
            item {
                FavoriteSection(
                    modifier = Modifier,
                    onFavoriteClick = {}
                )
            }
        } else {
            items(favorites) { item ->
                FavoriteItemSection(
                    modifier = Modifier,
                    item = item
                )
            }
        }
    }
}
