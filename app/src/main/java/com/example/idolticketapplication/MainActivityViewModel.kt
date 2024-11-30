package com.example.idolticketapplication

import androidx.lifecycle.ViewModel
import com.example.idolticketapplication.room.EventListEntity
import com.example.idolticketapplication.room.OwnedTicketsEntity

class MainActivityViewModel : ViewModel() {
    var selectTab = 0
    var ownedTicketsEntity: OwnedTicketsEntity? = null
    var eventListEntity: EventListEntity? = null
}