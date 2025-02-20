package com.example.eventlee.view

import com.example.eventlee.model.Event

data class UIState(
    val events : MutableList<Event>? = null,
    val eventsByDate: Map<Int, List<Event>>? = null,
    val eventData: Event? = null
)
