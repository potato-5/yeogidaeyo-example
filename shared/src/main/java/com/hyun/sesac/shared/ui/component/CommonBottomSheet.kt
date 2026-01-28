package com.hyun.sesac.shared.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBottomSheet(
    onDismiss: () -> Unit,
    bottomSheetState: SheetState,
    scrimColor: Color,
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        containerColor = Color.White,
        scrimColor = scrimColor,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = { BottomSheetDefaults.DragHandle()},
    ) {
        content()
    }
}

// Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        Box(modifier = Modifier.fillMaxSize()) {
            CommonBottomSheet(
                onDismiss = { },
                bottomSheetState = sheetState,
                scrimColor = Color.Black.copy(alpha = 0.32f)
            ) {

            }
        }
    }
}