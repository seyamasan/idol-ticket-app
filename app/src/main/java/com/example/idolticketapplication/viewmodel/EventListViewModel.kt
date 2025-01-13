package com.example.idolticketapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idolticketapplication.data.demoEvents
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.room.repository.EventListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventListViewModel(private val repository: EventListRepository) : ViewModel() {

    private val _events = MutableStateFlow<List<EventListEntity>?>(emptyList())
    val events: StateFlow<List<EventListEntity>?> get() = _events

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.selectAll()
            val sortedResult = result?.sortedBy { it.localDate } // 年月日をもとにソート
            withContext(Dispatchers.Main) {
                _events.value = sortedResult
            }
        }
    }

    // デモデータ挿入
    fun insertDummyData() {
        viewModelScope.launch(Dispatchers.IO) {
            demoEvents.forEach { demo ->
                val result = repository.insert(
                    ticketId = demo.ticketId,
                    date = demo.date,
                    startTime = demo.startTime,
                    endTime = demo.endTime,
                    place = demo.place,
                    eventName = demo.eventName,
                    genre = demo.genre,
                    idolName = demo.idolName,
                    detail = demo.detail,
                    price = demo.price,
                    stock = demo.stock,
                    sold = demo.sold,
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