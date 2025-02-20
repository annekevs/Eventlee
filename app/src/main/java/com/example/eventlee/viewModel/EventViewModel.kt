package com.example.eventlee.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eventlee.model.EventRepository
import com.example.eventlee.view.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.time.ZoneId

class EventViewModel(private val repository: EventRepository): ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    /**
     * @param id The reference of the document*/
    suspend fun onSelectEvent(
        id: String
    ) {
        withContext(Dispatchers.IO){
            try {
                val eventData = repository.getEvent(id)
                Log.e("REPO TAG", "data fetched $eventData")
                _uiState.value = _uiState.value.copy(
                    eventData = eventData
                )
                //openScreen()
            } catch (e: Exception) {
                Log.e("Event", e.localizedMessage!!)
                null
            }
        }
    }

    suspend fun getEvents(){
        withContext(Dispatchers.IO){
            try {
                val events = repository.fetchAllEvent()
                Log.e("REPO TAG", "data fetched $events")
                _uiState.value = _uiState.value.copy(
                    events = events,
                    //@TODO remove if doesn't work
                    eventsByDate = events.groupBy {
                        it.startOn.toInstant().atZone(ZoneId.systemDefault()).dayOfYear
                    }
                )
            } catch (e: Exception) {
                Log.e("Event", e.localizedMessage!!)
                null
            }
        }
    }
}