package com.example.idolticketapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.idolticketapplication.navigator.AppNavigatorImpl
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdolTicketApplicationTheme {
                val navController = rememberNavController()
                AppNavigatorImpl(navController).NavigateTo()
            }
        }
    }
}