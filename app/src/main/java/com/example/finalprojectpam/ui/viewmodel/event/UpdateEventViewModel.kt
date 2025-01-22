package com.example.finalprojectpam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.ui.view.event.DestinasiUpdateEvent
import kotlinx.coroutines.launch


class UpdateEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val eventRepository: EventRepository
): ViewModel(){
    var updateEventUiState by mutableStateOf(InsertEventUiState())
        private set

    private val _idEvent: Int = checkNotNull(savedStateHandle[DestinasiUpdateEvent.IdEvent])

    init {
        viewModelScope.launch {
            updateEventUiState = eventRepository.getEventbyidEvent(_idEvent)
                .toUiStateEvnt()
        }
    }

    fun updateInsertEvntState(insertEventUiEvent: InsertEventUiEvent){
        updateEventUiState = InsertEventUiState(insertEventUiEvent = insertEventUiEvent)
    }

    suspend fun updateEvnt(){
        viewModelScope.launch {
            try {
                eventRepository.updateEvent(_idEvent, updateEventUiState.insertEventUiEvent.toEvnt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
