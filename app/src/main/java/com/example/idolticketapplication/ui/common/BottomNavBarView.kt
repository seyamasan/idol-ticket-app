package com.example.idolticketapplication.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.idolticketapplication.screens.Screens
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

@Composable
fun BottomNavBarView(
    navController: NavHostController?,
    selectedTab: Int,
    onSelectedTab: (Int) -> Unit
) {
    NavigationBar {
        Screens.screenList.forEachIndexed { index, screenData ->
            NavigationBarItem(
                icon = { Icon(Screens.iconList[index], contentDescription = "Bottom nav bar icon.") },
                label = { Text(stringResource(id = screenData.screenTitleResId)) },
                selected = index == selectedTab,
                onClick = {
                    if (index != selectedTab) {
                        onSelectedTab(index)
                        navController?.navigate(screenData)
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarViewPreview() {
    IdolTicketApplicationTheme {
        BottomNavBarView(
            navController = null,
            selectedTab = 0,
            onSelectedTab = {}
        )
    }
}