package com.example.idolticketapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idolticketapplication.room.OwnedTicketsEntity
import com.example.idolticketapplication.room.repository.OwnedTicketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CheckConsumeTicketViewModel(private val repository: OwnedTicketsRepository) : ViewModel() {
    private val _updateResult = MutableLiveData<Boolean?>()
    val updateResult: LiveData<Boolean?> get() = _updateResult

    fun update(ownedTicketsEntity: OwnedTicketsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.update(ownedTicketsEntity)

            withContext(Dispatchers.Main) {
                _updateResult.value = result
            }
        }
    }

    fun reset() {
        _updateResult.value = null
    }
}