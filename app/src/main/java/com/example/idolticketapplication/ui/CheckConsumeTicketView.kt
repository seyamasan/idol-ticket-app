package com.example.idolticketapplication.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.ui.common.ConsumeAlertDialogExample
import com.example.idolticketapplication.ui.common.TicketCardView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import com.example.idolticketapplication.viewmodel.CheckConsumeTicketViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CheckConsumeTicketView(
    viewModel: CheckConsumeTicketViewModel = koinViewModel(),
    navController: NavHostController?,
    ticket: OwnedTicketsEntity,
    consumption: Int
) {
    val updateResult by viewModel.updateResult.observeAsState(null)
    var showConsumeDialog by rememberSaveable { mutableStateOf(false) }

    val ticketString = if (ticket.numberOfTickets > 1) {
        stringResource(id = R.string.tickets)
    } else {
        stringResource(id = R.string.ticket)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            item {
                TicketCardView(
                    ticketDate = ticket,
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
                            text = consumption.toString(),
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
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(id = R.string.check_consume_ticket_view_warning_1))
                    Text(stringResource(id = R.string.check_consume_ticket_view_warning_2))
                    Text(stringResource(id = R.string.check_consume_ticket_view_warning_3))
                }
            }

            item {
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
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        onClick = {
                            showConsumeDialog = true
                        }
                    ) {
                        Text(stringResource(id = R.string.check_consume_ticket_view_consume))
                    }
                }
            }
        }

        if (showConsumeDialog) {
            ConsumeAlertDialogExample(
                onDismissRequest = { showConsumeDialog = false },
                onConsume = {
                    showConsumeDialog = false
                    viewModel.update(
                        OwnedTicketsEntity(
                            id = ticket.id,
                            date = ticket.date,
                            startTime = ticket.startTime,
                            place = ticket.place,
                            genre = ticket.genre,
                            idolName = ticket.idolName,
                            numberOfTickets = ticket.numberOfTickets - consumption,
                            enable = ticket.enable
                        )
                    )
                },
                dialogTitle = stringResource(id = R.string.consume_dialog_title),
                dialogText = stringResource(id = R.string.consume_dialog_msg),
                icon = Icons.Default.Warning
            )
        }

        if (updateResult != null) {
            // いつかエラー処理分けしたい
            navController?.popBackStack()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckConsumeTicketViewPreview() {
    IdolTicketApplicationTheme {
        CheckConsumeTicketView(
            navController = null,
            ticket = OwnedTicketsEntity( // dummy
                id = 0,
                date = "2025/04/01",
                startTime = "17:00",
                place = "タワーレコード渋谷店",
                genre = "チェキ会",
                idolName = "カミヤサキ",
                numberOfTickets = 3,
                enable = true
            ),
            consumption = 2
        )
    }
}