package com.example.eventlee

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object EventDetail : Routes("details")
}