package com.example.idolticketapplication.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.idolticketapplication.R

@Composable
fun ConsumeAlertDialogExample(
    onDismissRequest: () -> Unit,
    onConsume: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onConsume()
                }
            ) {
                Text(stringResource(id = R.string.check_consume_ticket_view_consume))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(id = R.string.check_consume_ticket_view_cancel))
            }
        }
    )
}