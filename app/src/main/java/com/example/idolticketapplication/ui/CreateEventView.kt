package com.example.idolticketapplication.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventView(
    navController: NavHostController?,
    screenTitle: String
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedStartTime by rememberSaveable { mutableStateOf("") }
    var selectedEndTime by rememberSaveable { mutableStateOf("") }
    var showStartTimeDialog by rememberSaveable { mutableStateOf(false) }
    var showEndTimeDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBarView(
                navController = navController,
                title = screenTitle,
                enableTitle = true,
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
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.date_and_time_section),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )

                    ReadOnlyDatePickerDialog(
                        showDatePicker = showDatePicker,
                        datePickerState = datePickerState,
                        onTextFieldClick = { showDatePicker = true },
                        onDateSelected = {
                            showDatePicker = false
                            print(it)
                        }
                    )

                    ReadOnlyTimePickerDialog(
                        selectedStartTime = selectedStartTime,
                        selectedEndTime = selectedEndTime,
                        showStartTimeDialog = showStartTimeDialog,
                        showEndTimeDialog = showEndTimeDialog,
                        onShowStartTimeDialog = {
                            showStartTimeDialog = true
                            showEndTimeDialog = false
                        },
                        onShowEndTimeDialog = {
                            showStartTimeDialog = false
                            showEndTimeDialog = true
                        },
                        onStartConfirm = { newValue ->
                            selectedStartTime = "%02d:%02d".format(newValue.first, newValue.second)
                            showStartTimeDialog = false
                            showEndTimeDialog = false
                        },
                        onEndConfirm = { newValue ->
                            selectedEndTime = "%02d:%02d".format(newValue.first, newValue.second)
                            showStartTimeDialog = false
                            showEndTimeDialog = false
                        },
                        onDismiss = {
                            showStartTimeDialog = false
                            showEndTimeDialog = false
                        }
                    )
                }
            }
            CreateEventButton {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadOnlyDatePickerDialog(
    showDatePicker: Boolean,
    datePickerState: DatePickerState,
    onTextFieldClick: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text(stringResource(id = R.string.create_event_data)) },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Create Event View DateRange Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        onTextFieldClick()
                    }
                }
            }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {}, // 他要素タップしても閉じさせない
            confirmButton = {
                TextButton(onClick = { onDateSelected(selectedDate) }) {
                    Text("OK")
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                DatePicker(
                    showModeToggle = false, // カレンダーのみで入力モードなし
                    state = datePickerState
                )
            }
        }
    }
}

@Composable
private fun CreateEventButton(onCreateClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(
            modifier = Modifier.padding(8.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
            onClick = onCreateClick
        ) {
            Text(text = stringResource(id = R.string.create_event_button))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReadOnlyTimePickerDialog(
    selectedStartTime: String,
    selectedEndTime: String,
    showStartTimeDialog: Boolean,
    showEndTimeDialog: Boolean,
    onShowStartTimeDialog: () -> Unit,
    onShowEndTimeDialog: () -> Unit,
    onStartConfirm: (Pair<Int, Int>) -> Unit,
    onEndConfirm: (Pair<Int, Int>) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = selectedStartTime,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.create_event_start_time)) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Create Event View Start Time Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .pointerInput(selectedStartTime) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            onShowStartTimeDialog()
                        }
                    }
                }
        )

        Text(text = "〜")

        OutlinedTextField(
            value = selectedEndTime,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.create_event_end_time)) },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Create Event View End Time Icon"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .pointerInput(selectedStartTime) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            onShowEndTimeDialog()
                        }
                    }
                }
        )
    }

    if (showStartTimeDialog || showEndTimeDialog) {
        TimePickerDialog(
            onDismiss = onDismiss,
            onConfirm = {
                if (showStartTimeDialog) {
                    onStartConfirm(Pair(timePickerState.hour, timePickerState.minute))
                } else {
                    onEndConfirm(Pair(timePickerState.hour, timePickerState.minute))
                }
            }
        ) {
            // 横にした時若干UI潰れているけど対策が分からない。。。
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                TimePicker(
                    state = timePickerState,
                )
            }
        }
    }
}

@Composable
private fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

private fun convertMillisToDate(millis: Long): String {
    // yyyy/MM/ddのフォーマットに変換
    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun CreateEventViewPreview() {
    IdolTicketApplicationTheme {
        CreateEventView(
            navController = null,
            screenTitle = stringResource(id = R.string.create_event_view_title)
        )
    }
}