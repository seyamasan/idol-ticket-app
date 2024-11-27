package com.example.idolticketapplication.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idolticketapplication.data.demoTickets
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.room.OwnedTicketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OwnedTicketsViewModel(private val repository: OwnedTicketsRepository) : ViewModel() {

    private val _tickets = MutableStateFlow<List<OwnedTicketsEntity>?>(emptyList())
    val tickets: StateFlow<List<OwnedTicketsEntity>?> get() = _tickets

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

    // デモデータ挿入
//    fun insertDummyData() {
//        viewModelScope.launch(Dispatchers.IO) {
//            demoTickets.forEach { demo ->
//                val result = repository.insert(
//                    date = demo.date,
//                    startTime = demo.time,
//                    place = demo.place,
//                    genre = demo.genre,
//                    idolName = demo.idolName,
//                    numberOfTickets = demo.numberOfTickets,
//                    enable = demo.enable
//                )
//                if (result) {
//                    print("Insert Success")
//                } else {
//                    print("Insert Failure")
//                }
//            }
//        }
//    }
}