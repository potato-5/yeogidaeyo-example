package com.hyun.sesac.home.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyun.sesac.domain.model.Parking
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.theme.PreviewTheme


// TODO 01/20 : 후행 람다, RES값 ( string.xml, const, color.xml 정리 )
@Composable
fun ParkingSpotDetail(
    selectedSpot: Parking?,
    isBookmarked: Boolean,
    currentCount: Int?,
    totalCount: Int?,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
    onRouteClick: () -> Unit,
) {
    ParkingSpotDetailSection(
        selectedSpot = selectedSpot,
        isBookmarked = isBookmarked,
        onBookmarkClick = onBookmarkClick,
        modifier = modifier
    )
    Spacer(modifier = Modifier.padding(bottom = 8.dp))

    ParkingDivider()
    ParkingStatusSection(
        total = totalCount,
        current = currentCount,
        onRouteClick = onRouteClick,
        modifier = modifier
    )
    ParkingInfoSection(
        selectedSpot = selectedSpot,
        modifier = modifier
    )
}

// Preview
@Preview(showBackground = true)
@Composable
fun ParkingSpotDetailPreview() {
    val dummyParking = Parking(
        name = stringResource(id = R.string.test),
        address = stringResource(id = R.string.test)
    )
    PreviewTheme {
        ParkingSpotDetail(
            selectedSpot = dummyParking,
            isBookmarked = true,
            currentCount = 3,
            totalCount = 10,
            onBookmarkClick = {},
            onRouteClick = {},
        )
    }
}