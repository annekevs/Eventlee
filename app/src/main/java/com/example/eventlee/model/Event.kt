package com.example.eventlee.model

import com.google.firebase.Timestamp

data class Event(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = defaultEventImage,
    val startOn: Timestamp = Timestamp.now(),
    val endOn: Timestamp = Timestamp.now(),
    val nbAttendees: Int = 0,
    val location: String = "online",
    val publisher: String = ""
)

val defaultEventImage = "https://img.freepik.com/free-photo/chef-working-together-professional-kitchen_23-2149727953.jpg?uid=R51948038&ga=GA1.1.1428676425.1735252603&semt=ais_hybrid"

