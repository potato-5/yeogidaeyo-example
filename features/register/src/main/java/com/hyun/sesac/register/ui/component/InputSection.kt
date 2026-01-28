package com.hyun.sesac.register.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyun.sesac.shared.R
import com.hyun.sesac.shared.ui.component.CommonIcon
import com.hyun.sesac.shared.ui.component.CommonWrapperCard
import com.hyun.sesac.shared.ui.theme.BodyText
import com.hyun.sesac.shared.ui.theme.CaptionText
import com.hyun.sesac.shared.ui.theme.MainIndigo
import com.hyun.sesac.shared.ui.theme.PreviewTheme
import com.hyun.sesac.shared.ui.theme.SoftIndigo

@Composable
fun InputSection(
    floor: String,
    memo: String,
    modifier: Modifier = Modifier,
    onMemoChange: (String) -> Unit,
    onFloorChange: (Int) -> Unit,
    ){
    CommonWrapperCard(
        modifier = modifier.padding(top = 1.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.parking_floor),
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyText
                )
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
                        onClick = { onFloorChange(-1) }
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
                        text = floor,
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
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
                        onClick = { onFloorChange(1) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_floor),
                            tint = MainIndigo
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = SoftIndigo,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {

                CommonIcon(
                    icon = Icons.Default.EditNote,
                    iconPadding = 8.dp,
                    iconColor = MainIndigo,
                    modifier = Modifier
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.oneline_memo),
                        style = MaterialTheme.typography.bodySmall,
                        color = CaptionText,
                        modifier = Modifier
                            .offset(y = 10.dp)
                            .padding(start = 15.dp, top = 5.dp)
                    )

                    TextField(
                        value = memo,
                        onValueChange = onMemoChange,
                        placeholder = { Text(
                            text = stringResource(id = R.string.example_memo),
                            style = MaterialTheme.typography.bodyLarge) },
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = BodyText,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MainIndigo,
                            focusedPlaceholderColor = BodyText,
                            unfocusedPlaceholderColor = BodyText
                        ),
                        singleLine = true
                    )
                }
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun InputSectionPreview() {
    PreviewTheme {
        InputSection(
            floor = "1",
            memo = stringResource(id = R.string.example_memo),
            onMemoChange = {},
            onFloorChange = {},
        )
    }
}