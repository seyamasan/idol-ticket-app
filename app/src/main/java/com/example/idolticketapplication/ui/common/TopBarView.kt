package com.example.idolticketapplication.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

/*
* CommonTopAppBar（共通のトップバー）
*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarView(
    navController: NavHostController?,
    title: String,
    enableTitle: Boolean = true,
    enableBack: Boolean = false
) {
    TopAppBar(
        title = {
            if (enableTitle) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            if (enableBack) {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "TopBarView Back")
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarViewPreview() {
    IdolTicketApplicationTheme {
        TopBarView(
            navController = null,
            title = "Test View",
            enableTitle = true,
            enableBack = true
        )
    }
}