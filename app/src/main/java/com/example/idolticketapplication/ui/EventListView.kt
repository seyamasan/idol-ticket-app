package com.example.idolticketapplication.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.data.EventGenre
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.ui.common.BottomNavBarView
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import com.example.idolticketapplication.viewmodel.EventListViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventListView(
    viewModel: EventListViewModel = koinViewModel(),
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    val events by viewModel.events.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllItems()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBarView(screenTitle) },
        bottomBar = {
            BottomNavBarView(
                navController = navController,
                selectedTab = selectedTab,
                onSelectedTab = { onSelectedTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EventListByGenreWithCards(events)
        }
    }
}

@Composable
private fun EventListByGenreWithCards(events: List<EventListEntity>?) {
    // ジャンルごとにグループ化
    val eventsByGenre = events?.groupBy { it.genre }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        eventsByGenre?.forEach { (genre, events) ->
            item {
                // セクションヘッダー
                Text(
                    text = stringResource(id = EventGenre.getStringResId(genre)),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // チケット一覧 (横スクロール)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    events.forEach { event ->
                        item {
                            EventCard(event = event)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EventCard(event: EventListEntity) {
    ElevatedCard(
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 画像入る予定。無い場合は以下のアイコンとか。
            Icon(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                imageVector = Icons.Default.BrokenImage,
                contentDescription = "BrokenImage icon"
            )

            // イベント名
            Text(
                text = event.eventName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // アイドル名
            Text(
                text = event.idolName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            // 日程
            Text(
                text = event.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EventListViewPreview() {
    IdolTicketApplicationTheme {
        EventListView(
            navController = null,
            screenTitle = stringResource(id = R.string.event_list_screen_title),
            selectedTab = 1,
            onSelectedTab = {}
        )
    }
}