package com.example.idolticketapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.state.QuantityPickerState


class EventDetailViewModel: ViewModel() {
    private val _quantityPickerState = mutableStateOf<QuantityPickerState?>(null)
    val quantityPickerState: QuantityPickerState
        get() = _quantityPickerState.value
            ?: QuantityPickerState(minQuantity = 0, maxQuantity = 0, initialQuantity = 0) // dummy
                .also { _quantityPickerState.value = it }

    private var _init: Boolean = false


    fun setQuantityPickerState(entity: EventListEntity) {
        if (!_init) {
            _init = true
            _quantityPickerState.value = QuantityPickerState(
                minQuantity = 0,
                maxQuantity = entity.stock - entity.sold,
                initialQuantity = 0
            )
        }
    }
}