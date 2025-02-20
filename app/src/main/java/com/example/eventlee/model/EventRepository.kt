package com.example.eventlee.model

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class EventRepository {

    private val firestore = Firebase.firestore

    suspend fun getEvent(eventId: String): Event?{
        val eventsCollection = firestore.collection("events")
        val docRef = eventsCollection.document(eventId)

        return try {
            docRef.get().await().toObject()
        }catch (e: Exception){
            Log.e("REPO TAG", "get failed with ", e)
            null
        }
    }

    suspend fun fetchAllEvent(): MutableList<Event>{
        return try {
            firestore.collection(EVENT_COLLECTION).get().await().toObjects(Event::class.java)
        } catch (e: Exception) {
            Log.e("EVENT", "Fetch all events failed")
            mutableListOf<Event>()
        }
    }

    suspend fun addEvent(event: Event){
        val eventsCollection = firestore.collection("events")
        eventsCollection.add(event)
            .addOnSuccessListener {doc ->
                // successfully
            }
            .addOnFailureListener{exception ->
                Log.e("ADD EVENT FAILED", exception.message.toString())
            }
    }

    companion object {
        private const val EVENT_COLLECTION = "events"
    }
}