package com.example.idolticketapplication.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

@Composable
fun TicketCardView(
    ticketDate: OwnedTicketsEntity,
    onClick: () -> Unit
) {
    BadgedBox(
        modifier = Modifier.padding(28.dp),
        badge = {
            if (ticketDate.numberOfTickets > 0) {
                Badge(
                    modifier = Modifier.size(40.dp),
                    containerColor = Color.Red,
                    contentColor = Color.White
                ) {
                    Text(
                        text = "${ticketDate.numberOfTickets}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick()
                },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // チケットタイトル
                Text(
                    text = "🎟️ ${ticketDate.genre}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 日時情報
                Text(
                    text = "Date: ${ticketDate.date}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Time: ${ticketDate.startTime}〜",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Place: ${ticketDate.place}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                // アイドル名
                Text(
                    text = ticketDate.idolName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF6200EA)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // チケット番号風
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Ticket No.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Text(
                        text = ticketDate.id.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketCardViewPreview() {
    IdolTicketApplicationTheme {
        // dummy data
        TicketCardView(
            ticketDate = OwnedTicketsEntity(
                id = 0,
                date = "2025/04/01",
                startTime = "17:00",
                place = "タワーレコード渋谷店",
                genre = "チェキ会",
                idolName = "カミヤサキ",
                numberOfTickets = 3,
                enable = true
            ),
            onClick = {}
        )
    }
}