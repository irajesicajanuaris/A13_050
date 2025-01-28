package com.example.finalprojectpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.repository.TiketRepository
import com.example.finalprojectpam.ui.view.peserta.DestinasiUpdatePeserta
import com.example.finalprojectpam.ui.view.tiket.DestinasiUpdateTiket
import kotlinx.coroutines.launch


class UpdateTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tkt: TiketRepository,
    private val evnt: EventRepository,
    private val psrt: PesertaRepository,
): ViewModel(){
    var updateTiketUiState by mutableStateOf(InsertTiketUiState())
        private set


    private val _idTiket: Int = checkNotNull(savedStateHandle[DestinasiUpdateTiket.IdTiket])
    var eventList by mutableStateOf<List<Event>>(emptyList())
        private set
    var pesertaList by mutableStateOf<List<Peserta>>(emptyList())
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            loadEvent()
            loadPeserta()
            loadTiket()
        }
    }
    private suspend fun loadEvent() {
        try {
            eventList = evnt.getAllEvents()
        } catch (e: Exception) {
            errorMessage = "Failed to load tiket data"
            e.printStackTrace()
        }
    }
    private suspend fun loadPeserta() {
        try {
            pesertaList = psrt.getAllPesertas()
        } catch (e: Exception) {
            errorMessage = "Failed to load tiket data"
            e.printStackTrace()
        }
    }
    private suspend fun loadTiket() {
        try {
            val tiket = tkt.getTiketbyidTiket(_idTiket)
            updateTiketUiState = tiket.toUiStateTkt()
        } catch (e: Exception) {
            errorMessage = "Failed to load tiket data"
            e.printStackTrace()
        }
    }


    fun updateInsertTktState(insertTiketUiEvent: InsertTiketUiEvent){
        updateTiketUiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun updateTkt() {
        viewModelScope.launch {
            try {
                tkt.updateTiket(_idTiket, updateTiketUiState.insertTiketUiEvent.toTkt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
