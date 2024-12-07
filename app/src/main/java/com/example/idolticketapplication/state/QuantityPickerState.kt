package com.example.idolticketapplication.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

class QuantityPickerState(
    private val minQuantity: Int,
    private val maxQuantity: Int,
    initialQuantity: Int
) {
    var quantity by mutableIntStateOf(
        initialQuantity.coerceIn(minQuantity, maxQuantity)
    )

    fun isMaxQuantity() = quantity >= maxQuantity
    fun isMinQuantity() = quantity <= minQuantity

    fun increase() {
        if (!isMaxQuantity()) {
            quantity++
        }
    }

    fun decrease() {
        if (!isMinQuantity()) {
            quantity--
        }
    }
}