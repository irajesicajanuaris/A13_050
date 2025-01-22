package com.example.finalprojectpam.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.repository.EventRepository
import kotlinx.coroutines.launch


class DetailEventViewModel(private val evntRepository: EventRepository) : ViewModel() {

    var EventuiState by mutableStateOf(DetailEventUiState())
        private set

    fun fetchDetailEvent(idEvent: Int) {
        viewModelScope.launch {
            EventuiState = DetailEventUiState(isLoading = true)
            try {
                val event = evntRepository.getEventbyidEvent(idEvent)
                EventuiState = DetailEventUiState(detailEventUiEvent = event.toDetailEventUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                EventuiState = DetailEventUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }

}

data class DetailEventUiState(
    val detailEventUiEvent: InsertEventUiEvent = InsertEventUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailEventUiEvent != InsertEventUiEvent()
}

fun Event.toDetailEventUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent(
        id_event= id_event,
        nama_event = nama_event,
        deskripsi_event = deskripsi_event,
        tanggal_event = tanggal_event,
        lokasi_event = lokasi_event
    )
}