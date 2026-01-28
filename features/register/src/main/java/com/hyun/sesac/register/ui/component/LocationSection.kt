package com.hyun.sesac.register.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyun.sesac.register.R
import com.hyun.sesac.shared.ui.component.CommonIcon
import com.hyun.sesac.shared.ui.component.CommonWrapperCard
import com.hyun.sesac.shared.ui.theme.HeadingTitle
import com.hyun.sesac.shared.ui.theme.MainIndigo
import com.hyun.sesac.shared.ui.theme.NeutralGray
import com.hyun.sesac.shared.ui.theme.PreviewTheme


@Composable
fun LocationSection(
    locationName: String,
    parkingSpot: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CommonWrapperCard(
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonIcon(
                    icon = Icons.Default.LocationOn,
                    iconPadding = 8.dp,
                    iconColor = MainIndigo,
                    modifier = Modifier
                )
                /*Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    modifier = Modifier
                        .size(48.dp),
                    shadowElevation = 2.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF6200EE),
                        modifier = Modifier.padding(8.dp)
                    )
                }*/

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = locationName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeutralGray
                    )
                    Text(
                        text = parkingSpot,
                        style = MaterialTheme.typography.titleSmall,
                        color = HeadingTitle
                    )
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.car_3d_image),
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopEnd)
                .offset(y = (-30).dp)
        )
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    PreviewTheme {
        LocationSection(
            locationName = stringResource(id = com.hyun.sesac.shared.R.string.example_memo),
            parkingSpot = stringResource(id = com.hyun.sesac.shared.R.string.example_memo),
        )
    }
}
