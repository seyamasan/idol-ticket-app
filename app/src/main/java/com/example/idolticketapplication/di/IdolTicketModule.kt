package com.example.idolticketapplication.di

import com.example.idolticketapplication.room.OwnedTicketsDataBase
import com.example.idolticketapplication.room.OwnedTicketsRepository
import com.example.idolticketapplication.viewmodel.CheckConsumeTicketViewModel
import com.example.idolticketapplication.viewmodel.OwnedTicketsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val idolTicketModule = module {
    single { OwnedTicketsDataBase.buildDatabase(androidContext()).ownedTicketsDao() }
    single { OwnedTicketsRepository(get()) }
    viewModel { OwnedTicketsViewModel(get()) }
    viewModel { CheckConsumeTicketViewModel(get()) }
}