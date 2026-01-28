package com.hyun.sesac.shared.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonIcon(
    icon: ImageVector,
    iconPadding: Dp,
    iconColor: Color,
    modifier: Modifier = Modifier,
    ){

    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.size(48.dp),
        shadowElevation = 3.dp
    ){
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.padding(iconPadding)
        )
    }
}