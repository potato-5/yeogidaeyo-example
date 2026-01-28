package com.hyun.sesac.home.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.theme.PreviewTheme


@Composable
fun ParkingInfoSection(
    selectedSpot: Parking?,
    modifier: Modifier = Modifier
) {
    if (selectedSpot == null) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        ParkingInfoRow(
            label = stringResource(id = R.string.open_close_time),
            value = selectedSpot.address
        )
        ParkingDivider()

        ParkingInfoRow(
            label = stringResource(id = R.string.parking_fee),
            value = selectedSpot.name,
            subLabel = "최초 30분",
            isFree = true
        )
        ParkingDivider()

        ParkingInfoRow(
            label = stringResource(id = R.string.extra_fee),
            value = selectedSpot.name,
            subLabel = "10분당",
            isHighlight = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ParkingInfoPreview() {
    PreviewTheme {
        ParkingInfoSection(
            selectedSpot = Parking(
                name = stringResource(id = R.string.test),
                address = stringResource(id = R.string.test),
            )
        )
    }
}