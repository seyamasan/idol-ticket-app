package com.example.idolticketapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

@Composable
fun EventDetailView(
    navController: NavHostController?,
    event: EventListEntity
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarView(
                navController = navController,
                title = "",
                enableTitle = false,
                enableBack = true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // 画像入る予定。無い場合は以下のアイコンとか。
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium),
                imageVector = Icons.Default.BrokenImage,
                contentDescription = "BrokenImage icon"
            )

            // イベント名
            Text(
                text = event.eventName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            // 日時
            Text(
                text = event.date,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = "${event.startTime}〜${event.endTime}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            // アイドル名
            Text(
                text = event.idolName,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            // 詳細
            Text(
                text = event.detail,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            // 価格
            Text(
                text = event.price.toString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun EventDetailViewPreview() {
    IdolTicketApplicationTheme {
        EventDetailView(
            navController = null,
            event = EventListEntity(
                id = 0,
                date = "2025/04/01",
                startTime = "17:00",
                endTime = "18:00",
                place = "タワーレコード渋谷店",
                eventName = "めっちゃチェキ会",
                genre = 1,
                idolName = "カミヤサキ",
                detail = "カミヤサキとチェキ撮れます。",
                price = 500,
                stock = 90,
                sold = 0,
                enable = true
            )
        )
    }
}