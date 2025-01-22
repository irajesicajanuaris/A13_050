package com.example.finalprojectpam.repository

import com.example.finalprojectpam.model.AllEventResponse
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.service_api.EventService
import okio.IOException

interface EventRepository {
    suspend fun insertEvent(event: Event)
    suspend fun getEvent(): AllEventResponse
    suspend fun updateEvent(id_event: Int, event: Event)
    suspend fun deleteEvent(id_event: Int)
    suspend fun getEventbyidEvent(id_event: Int): Event
}

class NetworkEventRepository(
    private val eventApiService: EventService
) : EventRepository {

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(id_event: Int, event: Event) {
        eventApiService.updateEvent(id_event, event)
    }

    override suspend fun getEvent(): AllEventResponse {
        return eventApiService.getAllEvent()
    }

    override suspend fun deleteEvent(id_event: Int) {
        try {
            val response = eventApiService.deleteEvent(id_event)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete kontak. HTTP Status code: " + "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEventbyidEvent(id_event: Int): Event {
        return eventApiService.getEventbyidEvent(id_event).data
    }
}
