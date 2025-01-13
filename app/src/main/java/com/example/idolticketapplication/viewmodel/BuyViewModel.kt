package com.example.idolticketapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.room.repository.EventListRepository
import com.example.idolticketapplication.room.repository.OwnedTicketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuyViewModel(
    private val ownedTicketsRepository: OwnedTicketsRepository,
    private val eventListRepository: EventListRepository
) : ViewModel() {
    private val _eventListResult = MutableLiveData<Boolean?>()
    val eventListResult: LiveData<Boolean?> get() = _eventListResult

    private val _ownedTicketsResult = MutableLiveData<Boolean?>()
    val ownedTicketsResult: LiveData<Boolean?> get() = _ownedTicketsResult

    fun update(eventListEntity: EventListEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = eventListRepository.update(eventListEntity)

            withContext(Dispatchers.Main) {
                _eventListResult.value = result
            }
        }
    }

    fun insert(ownedTicketsEntity: OwnedTicketsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = ownedTicketsRepository.insert(
                ticketId = ownedTicketsEntity.ticketId,
                date = ownedTicketsEntity.date,
                startTime = ownedTicketsEntity.startTime,
                endTime = ownedTicketsEntity.endTime,
                place = ownedTicketsEntity.place,
                eventName = ownedTicketsEntity.eventName,
                genre = ownedTicketsEntity.genre,
                idolName = ownedTicketsEntity.idolName,
                detail = ownedTicketsEntity.detail,
                numberOfTickets = ownedTicketsEntity.numberOfTickets,
                enable = ownedTicketsEntity.enable
            )

            withContext(Dispatchers.Main) {
                _ownedTicketsResult.value = result
            }
        }
    }

    fun resetEventListResult() {
        _eventListResult.value = null
    }

    fun resetOwnedTicketsResult() {
        _ownedTicketsResult.value = null
    }
}