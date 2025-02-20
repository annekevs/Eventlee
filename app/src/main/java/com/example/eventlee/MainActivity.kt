package com.example.eventlee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventlee.model.EventRepository
import com.example.eventlee.view.EventDetailScreen
import com.example.eventlee.view.HomeScreen
import com.example.eventlee.viewModel.EventViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val eventViewModel = EventViewModel(EventRepository())

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        // First route : Home
        composable(Routes.Home.route) {
            // Lay down the Home Composable
            // and pass the navController
            HomeScreen(
                navController = navController,
                viewModel = eventViewModel
            )
        }

        // Another Route : EventDetail
        composable(Routes.EventDetail.route+"/{id}") { navBackStack ->
            // Extracting the argument
            val eventId = navBackStack.arguments?.getString("id")
            val state = eventViewModel.uiState.collectAsState()

            // EventDetail Screen
            state.value.eventData.let {
                if(it != null) {
                    EventDetailScreen(
                        navController = navController,
                        event = it
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}