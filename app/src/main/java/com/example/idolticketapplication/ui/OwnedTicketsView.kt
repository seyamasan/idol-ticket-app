package com.example.idolticketapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.data.TicketData
import com.example.idolticketapplication.data.demoTickets
import com.example.idolticketapplication.ui.common.BottomNavBarView
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun OwnedTicketsView(
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    var selectedTicketData by rememberSaveable { mutableStateOf<TicketData?>(null) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var tickets by rememberSaveable { mutableStateOf(demoTickets) }

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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            tickets.forEach {
                item {
                    TicketCardView(
                        ticketDate = it,
                        onClick = {
                            showBottomSheet = true
                            selectedTicketData = it
                        }
                    )
                }
            }
        }

        if (showBottomSheet && selectedTicketData != null) {
            UseTheTicketSheet(
                selectedTicketData = selectedTicketData!!,
                onDismissRequest = {
                    showBottomSheet = false
                    selectedTicketData = null
                },
                onChangeTicketData = { newData ->
                    tickets = tickets.map { ticket ->
                        if (ticket.id == newData.id) {
                            newData
                        } else {
                            ticket
                        }
                    }
                    selectedTicketData = null
                }
            )
        }
    }
}

@Composable
private fun TicketCardView(
    ticketDate: TicketData,
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
                    text = "Time: ${ticketDate.time}〜",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UseTheTicketSheet(
    selectedTicketData: TicketData,
    onDismissRequest: () -> Unit,
    onChangeTicketData: (TicketData) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var consumption by rememberSaveable { mutableIntStateOf(0) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // 左右端に配置
            verticalAlignment = Alignment.CenterVertically // 垂直方向の中央揃え
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
                    contentDescription = "Check icon"
                )
            }

            IconButton(
                onClick = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onChangeTicketData(
                                TicketData(
                                    id = selectedTicketData.id,
                                    date = selectedTicketData.date,
                                    time = selectedTicketData.time,
                                    place = selectedTicketData.place,
                                    genre = selectedTicketData.genre,
                                    idolName = selectedTicketData.idolName,
                                    numberOfTickets = selectedTicketData.numberOfTickets - consumption
                                )
                            )
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
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.support_use_the_ticket))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 枠線付きの枚数表示
                Box(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = consumption.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 96.sp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (consumption + 1 <= selectedTicketData.numberOfTickets) {
                                consumption += 1
                            }
                        }
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
                        onClick = {
                            if (consumption - 1 >= 0) {
                                consumption -= 1
                            }
                        }
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
fun OwnedTicketsViewPreview() {
    IdolTicketApplicationTheme {
        OwnedTicketsView(
            navController = null,
            screenTitle = stringResource(id = R.string.owned_tickets_screen_title),
            selectedTab = 0,
            onSelectedTab = {}
        )
    }
}
