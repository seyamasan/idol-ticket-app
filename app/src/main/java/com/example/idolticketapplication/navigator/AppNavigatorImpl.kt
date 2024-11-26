package com.example.idolticketapplication.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.idolticketapplication.screens.Screens
import com.example.idolticketapplication.ui.EventListView
import com.example.idolticketapplication.ui.OwnedTicketsView

class AppNavigatorImpl (private val navController: NavHostController) : AppNavigator {

    @Composable
    override fun NavigateTo() {
        var selectedTab by rememberSaveable { mutableIntStateOf(0) }
        NavHost(
            navController = navController, Screens.screenList.first() // 初期表示画面
        ) {
            // OwnedTicketsView
            composable<Screens.OwnedTicketsView> { backStackEntry ->
                val ownedTicketsView: Screens.OwnedTicketsView = backStackEntry.toRoute()
                OwnedTicketsView(
                    navController = navController,
                    screenTitle = stringResource(id = ownedTicketsView.screenTitleResId),
                    selectedTab = selectedTab,
                    onSelectedTab = { selectedTab = it }
                )
            }

            // EventListView
            composable<Screens.EventListView> { backStackEntry ->
                val eventListView: Screens.EventListView = backStackEntry.toRoute()
                EventListView(
                    navController = navController,
                    screenTitle = stringResource(id = eventListView.screenTitleResId),
                    selectedTab = selectedTab,
                    onSelectedTab = { selectedTab = it }
                )
            }
        }
    }
}