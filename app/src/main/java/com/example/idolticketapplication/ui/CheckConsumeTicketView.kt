package com.example.idolticketapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.data.TicketData
import com.example.idolticketapplication.ui.common.TicketCardView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

@Composable
fun CheckConsumeTicketView(
    navController: NavHostController?
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item {
                TicketCardView(
                    // dummy data 本当は引数で取ってくる
                    ticketDate = TicketData(
                        id = 0,
                        date = "2025/04/01",
                        time = "17:00",
                        place = "タワーレコード渋谷店",
                        genre = "チェキ会",
                        idolName = "カミヤサキ",
                        numberOfTickets = 3
                    ),
                    onClick = {}
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "2", // 本当は引数で取ってくる
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 96.sp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "枚",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckConsumeTicketViewPreview() {
    IdolTicketApplicationTheme {
        CheckConsumeTicketView(
            navController = null
        )
    }
}