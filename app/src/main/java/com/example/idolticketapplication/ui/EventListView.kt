package com.example.idolticketapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.idolticketapplication.R
import com.example.idolticketapplication.ui.common.BottomNavBarView
import com.example.idolticketapplication.ui.common.TopBarView
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

@Composable
fun EventListView(
    navController: NavHostController?,
    screenTitle: String,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { TopBarView(screenTitle) },
        bottomBar = {
            BottomNavBarView(
                navController = navController,
                selectedTab = selectedTab,
                onSelectedTab = { onSelectedTab(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventListViewPreview() {
    IdolTicketApplicationTheme {
        EventListView(
            navController = null,
            screenTitle = stringResource(id = R.string.event_list_screen_title),
            selectedTab = 1,
            onSelectedTab = {}
        )
    }
}