package com.example.idolticketapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idolticketapplication.data.demoTickets
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.room.repository.OwnedTicketsRepository
import com.example.idolticketapplication.state.QuantityPickerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OwnedTicketsViewModel(private val repository: OwnedTicketsRepository) : ViewModel() {

    private val _tickets = MutableStateFlow<List<OwnedTicketsEntity>?>(emptyList())
    val tickets: StateFlow<List<OwnedTicketsEntity>?> get() = _tickets

    private val _quantityPickerState = mutableStateOf<QuantityPickerState?>(null)
    val quantityPickerState: QuantityPickerState
        get() = _quantityPickerState.value
            ?: QuantityPickerState(minQuantity = 0, maxQuantity = 0, initialQuantity = 0) // dummy
                .also { _quantityPickerState.value = it }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.selectAll()
            val sortedResult = result?.sortedBy { it.localDate } // 年月日をもとにソート

            withContext(Dispatchers.Main) {
                _tickets.value = sortedResult
            }
        }
    }

    fun setQuantityPickerState(entity: OwnedTicketsEntity) {
        _quantityPickerState.value = QuantityPickerState(
            minQuantity = 0,
            maxQuantity = entity.numberOfTickets,
            initialQuantity = 0
        )
    }

    // デモデータ挿入
    fun insertDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            demoTickets.forEach { demo ->
                val result = repository.insert(
                    date = demo.date,
                    startTime = demo.startTime,
                    endTime = demo.endTime,
                    place = demo.place,
                    eventName = demo.eventName,
                    genre = demo.genre,
                    idolName = demo.idolName,
                    detail = demo.detail,
                    numberOfTickets = demo.numberOfTickets,
                    enable = demo.enable
                )
                if (result) {
                    print("Insert Success")
                } else {
                    print("Insert Failure")
                }
            }
        }
    }
}