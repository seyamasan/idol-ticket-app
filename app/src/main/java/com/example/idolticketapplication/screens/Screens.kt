package com.example.idolticketapplication.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Stadium
import com.example.idolticketapplication.R
import kotlinx.serialization.Serializable

class Screens {
    companion object {
        val screenList = listOf(
            OwnedTicketsView(),
            EventListView()
        )

        val iconList = listOf(
            Icons.Filled.LocalActivity,
            Icons.Filled.Stadium
        )
    }

    @Serializable
    data class OwnedTicketsView (
        override val screenTitleResId: Int = R.string.owned_tickets_screen_title
    ):ScreenData

    @Serializable
    data class EventListView (
        override val screenTitleResId: Int = R.string.event_list_screen_title
    ):ScreenData
}