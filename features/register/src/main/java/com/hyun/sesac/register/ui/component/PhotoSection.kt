package com.hyun.sesac.register.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.component.CommonWrapperCard
import com.hyun.sesac.shared.ui.theme.HeadingTitle
import com.hyun.sesac.shared.ui.theme.LightIndigo
import com.hyun.sesac.shared.ui.theme.MainIndigo
import com.hyun.sesac.shared.ui.theme.NeutralGray
import com.hyun.sesac.shared.ui.theme.PreviewTheme
import com.hyun.sesac.shared.ui.theme.PureWhite
import com.hyun.sesac.shared.ui.theme.SoftIndigo

@Composable
fun PhotoSection(
    modifier: Modifier = Modifier,
    editEnabled: Boolean,
    capturedImageUri: Any? = null,
    onTakePhotoClick: () -> Unit,
) {
    CommonWrapperCard(
        modifier = modifier.padding(top = 8.dp)
    ) {
        if (capturedImageUri == null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        color = SoftIndigo,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onTakePhotoClick() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = PureWhite,
                    modifier = Modifier.size(80.dp),
                    shadowElevation = 2.dp
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = stringResource(id = R.string.photo_shoot),
                        tint = MainIndigo,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(id = R.string.click_photo_my_car),
                    style = MaterialTheme.typography.titleSmall,
                    color = LightIndigo
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.register_photo_tip),
                    style = MaterialTheme.typography.bodySmall,
                    color = NeutralGray
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(SoftIndigo)
                    .clickable {
                        if(editEnabled)onTakePhotoClick()}) {
                AsyncImage(
                    model = capturedImageUri,
                    contentDescription = stringResource(id = R.string.my_parking_register_photo),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = stringResource(id = R.string.re_photo_shoot),
                    style = MaterialTheme.typography.bodySmall,
                    color = PureWhite
                )
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun PhotoSectionPreview() {
    PreviewTheme {
        PhotoSection(
            capturedImageUri = null,
            onTakePhotoClick = {},
            editEnabled = true,
        )
    }
}