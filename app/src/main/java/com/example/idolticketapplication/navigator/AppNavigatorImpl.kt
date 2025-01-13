package com.example.idolticketapplication.navigator

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.idolticketapplication.MainActivityViewModel
import com.example.idolticketapplication.screens.Screens
import com.example.idolticketapplication.ui.BuyView
import com.example.idolticketapplication.ui.CheckConsumeTicketView
import com.example.idolticketapplication.ui.CreateEventView
import com.example.idolticketapplication.ui.EventDetailView
import com.example.idolticketapplication.ui.EventListView
import com.example.idolticketapplication.ui.OwnedTicketsView

class AppNavigatorImpl (
    private val navController: NavHostController,
    private val viewModel: MainActivityViewModel
) : AppNavigator {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun NavigateTo() {
        var buySuccess by rememberSaveable { mutableStateOf(false) }

        NavHost(
            navController = navController, Screens.screenList.first() // 初期表示画面
        ) {
            // OwnedTicketsView
            composable<Screens.OwnedTicketsView> { backStackEntry ->
                val ownedTicketsView: Screens.OwnedTicketsView = backStackEntry.toRoute()
                viewModel.selectTab = 0
                OwnedTicketsView(
                    navController = navController,
                    screenTitle = stringResource(id = ownedTicketsView.screenTitleResId),
                    selectedTab = viewModel.selectTab,
                    onSelectedTab = { viewModel.selectTab = it },
                    onCheck = { viewModel.ownedTicketsEntity = it }
                )
            }

            // EventListView
            composable<Screens.EventListView> { backStackEntry ->
                val eventListView: Screens.EventListView = backStackEntry.toRoute()
                viewModel.selectTab = 1
                EventListView(
                    navController = navController,
                    screenTitle = stringResource(id = eventListView.screenTitleResId),
                    selectedTab = viewModel.selectTab,
                    onSelectedTab = { viewModel.selectTab = it },
                    onSelectedEvent = { viewModel.eventListEntity = it }
                )
            }

            // CheckConsumeTicketView
            composable<Screens.CheckConsumeTicketView> { backStackEntry ->
                val checkConsumeTicketView: Screens.CheckConsumeTicketView = backStackEntry.toRoute()
                val ownedTicketsEntity = viewModel.ownedTicketsEntity
                if (ownedTicketsEntity != null) {
                    CheckConsumeTicketView(
                        navController = navController,
                        ticket = ownedTicketsEntity,
                        consumption = checkConsumeTicketView.consumption
                    )
                } else {
                    Log.e("Navigation", "OwnedTicketsEntity data is null")
                }

                // 画面が破棄されるタイミングで値をリセット
                DisposableEffect(Unit) {
                    onDispose {
                        viewModel.ownedTicketsEntity = null
                    }
                }
            }

            // EventDetailView
            composable<Screens.EventDetailView> {
                val eventListEntity = viewModel.eventListEntity
                if (eventListEntity != null) {
                    EventDetailView(
                        navController = navController,
                        event = eventListEntity
                    )
                } else {
                    Log.e("Navigation", "EventListEntity data is null")
                }
            }

            // BuyView
            composable<Screens.BuyView> { backStackEntry ->
                val buyView: Screens.BuyView = backStackEntry.toRoute()
                val eventListEntity = viewModel.eventListEntity
                if (eventListEntity != null) {
                    BuyView(
                        navController = navController,
                        event = viewModel.eventListEntity!!,
                        buy = buyView.buy,
                        onSuccess = { buySuccess = true }
                    )
                } else {
                    Log.e("Navigation", "EventListEntity data is null")
                }

                DisposableEffect(Unit) {
                    onDispose {
                        if (buySuccess) {
                            buySuccess = false
                            viewModel.eventListEntity = null
                        }
                    }
                }
            }

            // CreateEventView
            composable<Screens.CreateEventView> { backStackEntry ->
                val createEventView: Screens.CreateEventView = backStackEntry.toRoute()
                CreateEventView(
                    navController = navController,
                    screenTitle = stringResource(id = createEventView.screenTitleResId)
                )
            }
        }
    }
}