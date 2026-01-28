package com.hyun.sesac.home.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.shared.R
import kotlinx.coroutines.selects.select

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationSection(
    onDismissRequest: () -> Unit,
    onAppSelected: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 48.dp)
        ){
            Text(
                text = stringResource(id = R.string.choose_navigation_app),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 48.dp, vertical = 12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            NavigationItem(
                name = stringResource(id = R.string.kakao_navi_app),
                icon = Icons.Default.Navigation,
                onClick = { onAppSelected("kakao") }
            )
        }
    }
}