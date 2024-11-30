package com.example.idolticketapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.idolticketapplication.navigator.AppNavigatorImpl
import com.example.idolticketapplication.ui.theme.IdolTicketApplicationTheme
import com.example.idolticketapplication.viewmodel.EventListViewModel
import com.example.idolticketapplication.viewmodel.OwnedTicketsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    // ダミーデータ生成用
//    private val dummyViewModel1: OwnedTicketsViewModel by viewModel()
//    private val dummyViewModel2: EventListViewModel by viewModel()

    private val viewModel: MainActivityViewModel by viewModels() // Koin から ViewModel を注入

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IdolTicketApplicationTheme {
                val navController = rememberNavController()
                AppNavigatorImpl(navController, viewModel).NavigateTo()
            }
        }

//        createDummyData()
    }

//    private fun createDummyData() {
//        dummyViewModel1.insertDummyData()
//        dummyViewModel2.insertDummyData()
//    }
}