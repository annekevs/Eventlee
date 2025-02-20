package com.example.eventlee.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eventlee.Routes
import com.example.eventlee.model.DateTime
import com.example.eventlee.view.component.CustomSearchBar
import com.example.eventlee.view.component.EventCardList
import com.example.eventlee.view.component.FilterTag
import com.example.eventlee.view.component.TopBar
import com.example.eventlee.view.component.countries
import com.example.eventlee.viewModel.EventViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: EventViewModel
) {
    //val previousRoute = navController.currentDestination?.route
    val state  = viewModel.uiState.collectAsState()
    val scope  = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getEvents()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar() }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.surfaceContainerLow
        ) {
            val headingSubText = "It’s a good to start any event,you can make important decision or plan them."
            var searchQuery by remember { mutableStateOf("") }
            var active by remember { mutableStateOf(false) }
            val filteredItems = countries.filter { it.contains(searchQuery.trim(), ignoreCase = true) }

            val meetNumber = 3
            var selectedEventGroup by remember { mutableStateOf(state.value.eventsByDate?.entries?.first()?.key) } // Selected group

            Column (modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                Text(
                    text = DateTime.getFormattedDate(),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text="You Have $meetNumber \nMeetings Today",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(bottom = 5.dp, top = 5.dp)
                )
                CustomSearchBar(
                    searchText = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearchText = { active = false },
                    active = active,
                    onActiveChange = { active = it },
                    placeholder = { Text(text = "Search by category")},
                    onClear = { searchQuery = ""},
                    items = filteredItems
                )
                FilterTag(
                    selectedItem = selectedEventGroup,
                    onSelectItem = { selectedEventGroup = it },
                    items = state.value.eventsByDate
                )
                Text(
                    text = headingSubText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray,
                    modifier = Modifier.padding(10.dp)
                )
                EventCardList(
                    selectedEventGroup = selectedEventGroup,
                    onSelect= { selectedEvent ->
                        scope.launch {
                            viewModel.onSelectEvent(
                                id = selectedEvent,
                            )
                        }
                        Log.d("EVENT Navigation ", "out of scope...")
                        navController.navigate(Routes.EventDetail.route + "/${selectedEvent}")

                    },
                    eventsByDate = state.value.eventsByDate
                )
            }
        }
    }
}
