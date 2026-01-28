package com.hyun.sesac.shared.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hyun.sesac.shared.ui.theme.LightIndigo
import com.hyun.sesac.shared.ui.theme.PureWhite

// TODO BUTTON -> SUCCESS / ERROR 컬러 설정
@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color = LightIndigo,
    contentColor: Color = PureWhite,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = PureWhite
        )
    }
}
