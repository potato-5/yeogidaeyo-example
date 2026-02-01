package com.hyun.sesac.register.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.theme.MainIndigo
import com.hyun.sesac.shared.ui.theme.SoftIndigo

@Composable
fun FloorCounter(
    floorText: String,
    editEnabled: Boolean = true,
    onUpClick: () -> Unit,
    onDownClick: () -> Unit,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = SoftIndigo,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        IconButton(
            enabled = editEnabled,
            onClick = { onDownClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(id = R.string.remove_floor),
                tint = MainIndigo
            )
        }

        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(24.dp)
                .background(SoftIndigo)
        )

        Text(
            text = floorText,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(32.dp),
            style = MaterialTheme.typography.titleMedium,
            color = MainIndigo,
        )

        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(24.dp)
                .background(SoftIndigo)
        )

        IconButton(
            enabled = editEnabled,
            onClick = { onUpClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_floor),
                tint = MainIndigo
            )
        }
    }
}