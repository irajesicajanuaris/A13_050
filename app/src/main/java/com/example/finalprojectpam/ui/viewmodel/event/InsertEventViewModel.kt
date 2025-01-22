package com.example.finalprojectpam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.repository.EventRepository
import kotlinx.coroutines.launch


class InsertEventViewModel (private val evnt: EventRepository): ViewModel(){
    var EventuiState by mutableStateOf(InsertEventUiState())
        private set

    fun updateInsertEvntState(insertEventUiEvent: InsertEventUiEvent){
        EventuiState = InsertEventUiState(insertEventUiEvent = insertEventUiEvent)
    }

    suspend fun insertEvnt(){
        viewModelScope.launch {
            try {
                evnt.insertEvent(EventuiState.insertEventUiEvent.toEvnt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertEventUiState(
    val insertEventUiEvent: InsertEventUiEvent = InsertEventUiEvent()
)

data class InsertEventUiEvent(
    val id_event: Int = 0,
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = "",
)

fun InsertEventUiEvent.toEvnt(): Event = Event(
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)

fun Event.toUiStateEvnt():InsertEventUiState = InsertEventUiState(
    insertEventUiEvent = toInsertUiEvent()
)

fun Event.toInsertUiEvent():InsertEventUiEvent = InsertEventUiEvent(
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)