package com.example.idolticketapplication.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.idolticketapplication.MainActivityViewModel
import com.example.idolticketapplication.screens.Screens
import com.example.idolticketapplication.ui.CheckConsumeTicketView
import com.example.idolticketapplication.ui.EventListView
import com.example.idolticketapplication.ui.OwnedTicketsView

class AppNavigatorImpl (
    private val navController: NavHostController,
    private val viewModel: MainActivityViewModel
) : AppNavigator {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun NavigateTo() {
        NavHost(
            navController = navController, Screens.screenList.first() // 初期表示画面
        ) {
            // OwnedTicketsView
            composable<Screens.OwnedTicketsView> { backStackEntry ->
                val ownedTicketsView: Screens.OwnedTicketsView = backStackEntry.toRoute()
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
                EventListView(
                    navController = navController,
                    screenTitle = stringResource(id = eventListView.screenTitleResId),
                    selectedTab = viewModel.selectTab,
                    onSelectedTab = { viewModel.selectTab = it }
                )
            }

            // CheckConsumeTicketView
            composable<Screens.CheckConsumeTicketView> { backStackEntry ->
                val checkConsumeTicketView: Screens.CheckConsumeTicketView = backStackEntry.toRoute()
                CheckConsumeTicketView(
                    navController = navController,
                    ticket = viewModel.ownedTicketsEntity!!,
                    consumption = checkConsumeTicketView.consumption
                )
            }
        }
    }
}