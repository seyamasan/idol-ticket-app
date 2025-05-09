package com.example.idolticketapplication.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material.icons.filled.Stadium
import com.example.idolticketapplication.R
import kotlinx.serialization.Serializable

class Screens {
    companion object {
        // Bottomのタブで使いたいものだけ
        val screenList = listOf(
            OwnedTicketsView(),
            EventListView()
        )

        val iconList = listOf(
            Icons.Filled.LocalActivity,
            Icons.Filled.Stadium
        )

        val createEventView = CreateEventView()
    }

    @Serializable
    data class OwnedTicketsView (
        override val screenTitleResId: Int = R.string.owned_tickets_screen_title
    ):ScreenData

    @Serializable
    data class EventListView (
        override val screenTitleResId: Int = R.string.event_list_screen_title
    ):ScreenData

    @Serializable
    data class CheckConsumeTicketView (
        val consumption: Int = 0
    )

    @Serializable
    object EventDetailView

    @Serializable
    data class BuyView (
        val buy: Int = 0
    )

    @Serializable
    data class CreateEventView (
        override val screenTitleResId: Int = R.string.create_event_view_title
    ):ScreenData
}