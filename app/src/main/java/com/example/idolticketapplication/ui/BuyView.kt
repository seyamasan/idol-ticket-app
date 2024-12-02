package com.example.idolticketapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.screens.Screens
import com.example.idolticketapplication.ui.common.EventStatusChipView
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import com.example.idolticketapplication.viewmodel.BuyViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun BuyView(
    viewModel: BuyViewModel = koinViewModel(),
    navController: NavHostController?,
    event: EventListEntity,
    buy: Int
) {
    val eventListResult by viewModel.eventListResult.observeAsState(null)
    val ownedTicketsResult by viewModel.ownedTicketsResult.observeAsState(null)
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    val ticketString = if (buy > 1) {
        stringResource(id = R.string.tickets)
    } else {
        stringResource(id = R.string.ticket)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarView(
                navController = navController,
                title = stringResource(id = R.string.buy_view_screen_title)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${String.format(Locale.US, "%,d", event.price)}円",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp)
            )

            Spacer(modifier = Modifier.height(56.dp))

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
                        text = buy.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = ticketString,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(56.dp))

            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(id = R.string.buy_view_text_1))
                Text(stringResource(id = R.string.buy_view_text_2))
                Text(stringResource(id = R.string.buy_view_text_3))
            }

            Spacer(modifier = Modifier.height(56.dp))

            Row(
                modifier = Modifier.clickable {
                    showBottomSheet = true
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info icon", tint = Color.Blue)
                Text(
                    text = stringResource(id = R.string.confirmation_of_details),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        color = Color.Blue
                    )
                )
            }

            Spacer(modifier = Modifier.height(56.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.White
                    ),
                    onClick = {
                        navController?.popBackStack()
                    }
                ) {
                    Text(stringResource(id = R.string.check_consume_ticket_view_cancel))
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ),
                    onClick = {
                        var enable = true
                        val sold = event.sold + buy
                        if (sold <= 0) {
                            enable = false
                        }
                        viewModel.update(
                            EventListEntity(
                                id = event.sold,
                                date = event.date,
                                startTime = event.startTime,
                                endTime = event.endTime,
                                place = event.place,
                                eventName = event.eventName,
                                genre = event.genre,
                                idolName = event.eventName,
                                detail = event.detail,
                                price = event.price,
                                stock = event.stock,
                                sold = sold,
                                enable = enable
                            )
                        )
                    }
                ) {
                    Text(stringResource(id = R.string.settlement))
                }
            }
        }

        if (showBottomSheet) {
            DetailSheet(
                event = event,
                onDismissRequest = { showBottomSheet = false }
            )
        }

        if (eventListResult != null) {
            // いつかエラー処理分けしたい
            viewModel.resetEventListResult()
            viewModel.insert(
                OwnedTicketsEntity(
                    id = 0, // 自動的にIDを入れるときは0を入れる
                    date = event.date,
                    startTime = event.startTime,
                    endTime = event.endTime,
                    place = event.place,
                    eventName = event.eventName,
                    genre = event.genre,
                    idolName = event.idolName,
                    detail = event.detail,
                    numberOfTickets = buy,
                    enable = true
                )
            )
        } else {
            viewModel.resetEventListResult()
        }

        if (ownedTicketsResult != null) {
            // いつかエラー処理分けしたい
            // というか値をnullとかにしてあげないと画面遷移できるようになってしまう。
            viewModel.resetOwnedTicketsResult()
            navController?.navigate(Screens.screenList.first())
        } else {
            viewModel.resetOwnedTicketsResult()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailSheet(
    event: EventListEntity,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column {
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
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                EventStatusChipView(enable = event.enable)
            }

            HorizontalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Text(
                text = "Date",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // 日時
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "${event.startTime}〜${event.endTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Text(
                text = "Place",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )

            // 場所
            Text(
                text = event.place,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Idol",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )

            // アイドル名
            Text(
                text = event.idolName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Detail",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.secondary
            )

            // 詳細
            Text(
                text = event.detail,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BuyViewPreview() {
    IdolTicketApplicationTheme {
        BuyView(
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
            ),
            buy = 3
        )
    }
}