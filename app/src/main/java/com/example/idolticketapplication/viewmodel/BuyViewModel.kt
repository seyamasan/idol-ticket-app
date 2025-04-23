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
            val ticket = ownedTicketsRepository.selectTicketIdAndEventName(
                ticketId = ownedTicketsEntity.ticketId,
                eventName = ownedTicketsEntity.eventName
            )
            var result = false
            ticket?.let { t ->
                // 持ってるならアップデート
                result = ownedTicketsRepository.update(
                    OwnedTicketsEntity(
                        id = t.id,
                        ticketId = t.ticketId,
                        date = t.date,
                        startTime = t.startTime,
                        endTime = t.endTime,
                        place = t.place,
                        eventName = t.eventName,
                        genre = t.genre,
                        idolName = t.idolName,
                        detail = t.detail,
                        numberOfTickets = t.numberOfTickets + ownedTicketsEntity.numberOfTickets,
                        enable = t.enable
                    )
                )
            }?: run {
                // 持っていないならそのまま登録
                result = ownedTicketsRepository.insert(
                    OwnedTicketsEntity(
                        id = 0, // 自動的にIDを入れるときは0を入れる
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
                )
            }

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