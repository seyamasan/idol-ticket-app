package com.example.idolticketapplication.di

import com.example.idolticketapplication.room.database.EventListDataBase
import com.example.idolticketapplication.room.database.OwnedTicketsDataBase
import com.example.idolticketapplication.room.repository.EventListRepository
import com.example.idolticketapplication.room.repository.OwnedTicketsRepository
import com.example.idolticketapplication.viewmodel.BuyViewModel
import com.example.idolticketapplication.viewmodel.CheckConsumeTicketViewModel
import com.example.idolticketapplication.viewmodel.EventDetailViewModel
import com.example.idolticketapplication.viewmodel.EventListViewModel
import com.example.idolticketapplication.viewmodel.OwnedTicketsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val idolTicketModule = module {
    single { OwnedTicketsDataBase.buildDatabase(androidContext()).ownedTicketsDao() }
    single { OwnedTicketsRepository(get()) }
    viewModel { OwnedTicketsViewModel(get()) }
    viewModel { CheckConsumeTicketViewModel(get()) }

    single { EventListDataBase.buildDatabase(androidContext()).eventListDao() }
    single { EventListRepository(get()) }
    viewModel { EventListViewModel(get()) }
    viewModel { BuyViewModel(ownedTicketsRepository = get(), eventListRepository = get()) }
    viewModel { EventDetailViewModel() }
}