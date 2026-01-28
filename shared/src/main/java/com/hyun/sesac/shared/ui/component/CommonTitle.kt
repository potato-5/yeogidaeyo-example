package com.hyun.sesac.shared.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.theme.HeadingTitle
import com.hyun.sesac.shared.ui.theme.PreviewTheme

@Composable
fun CommonTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = HeadingTitle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonTitlePreview() {
    PreviewTheme {
        CommonTitle(
            title = stringResource(id = R.string.mypage_title)
        )
    }
}