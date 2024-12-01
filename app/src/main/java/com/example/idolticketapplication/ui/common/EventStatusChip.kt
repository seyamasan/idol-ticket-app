package com.example.idolticketapplication.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idolticketapplication.R
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

@Composable
fun EventStatusChipView(enable: Boolean) {
    SuggestionChip(
        onClick = {},
        label = {
            if (enable) {
                Text(stringResource(id = R.string.status_now_accepting))
            } else {
                Text(stringResource(id = R.string.status_closed))
            }
        },
        border = if (enable) {BorderStroke(2.dp, Color.Green)} else {BorderStroke(2.dp, Color.Red)},
        colors = SuggestionChipDefaults.suggestionChipColors(
            labelColor = if (enable) {Color.Green} else {Color.Red}
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EventStatusChipViewPreview() {
    IdolTicketApplicationTheme {
        EventStatusChipView(enable = true)
    }
}