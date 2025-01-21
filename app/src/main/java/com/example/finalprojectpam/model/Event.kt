package com.example.finalprojectpam.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Event (
    val id_event: Int,
    val nama_event: String,
    val deskripsi_event: String,
    val tanggal_event: String,
    val lokasi_event: String
)

@Serializable
data class AllEventResponse(
    val status: Boolean,
    val message: String,
    val data: List<Event>
)

@Serializable
data class EventDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Event
)