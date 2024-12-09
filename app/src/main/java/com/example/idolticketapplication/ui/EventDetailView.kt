package com.example.idolticketapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.screens.Screens
import com.example.idolticketapplication.state.QuantityPickerState
import com.example.idolticketapplication.ui.common.EventStatusChipView
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import com.example.idolticketapplication.viewmodel.EventDetailViewModel
import com.example.idolticketapplication.viewmodel.OwnedTicketsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun EventDetailView(
    viewModel: EventDetailViewModel = koinViewModel(),
    navController: NavHostController?,
    event: EventListEntity
) {
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.setQuantityPickerState(event)
    }

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

            Box(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { showBottomSheet = true }) {
                    Text(text = stringResource(id = R.string.buy_now))
                }
            }
        }

        if (showBottomSheet) {
            BuyTheTicketSheet(
                event = event,
                state = viewModel.quantityPickerState,
                onDismissRequest = { showBottomSheet = false },
                onConfirm = {
                    showBottomSheet = false
                    navController?.navigate(Screens.BuyView(buy = it))
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuyTheTicketSheet(
    event: EventListEntity,
    state: QuantityPickerState,
    onDismissRequest: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismissRequest()
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Default icon"
                )
            }

            IconButton(
                onClick = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onConfirm(state.quantity)
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Check icon"
                )
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {},
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.how_to_buy),
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

                    Text(
                        text = "クレジットカード(VISA)",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "XXXX",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Text(text = stringResource(id = R.string.support_buy_msg))

            Text(
                text = "${String.format(Locale.US, "%,d", event.price)}円",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp)
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = state.quantity.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 96.sp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        enabled = !state.isMaxQuantity(),
                        onClick = { state.increase() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.Green, shape = CircleShape)
                                .padding(8.dp)
                        )
                    }

                    IconButton(
                        enabled = !state.isMinQuantity(),
                        onClick = { state.decrease() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Remove icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.Red, shape = CircleShape)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
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