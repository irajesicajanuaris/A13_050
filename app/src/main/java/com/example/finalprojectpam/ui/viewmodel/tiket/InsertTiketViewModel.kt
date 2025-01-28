package com.example.finalprojectpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.repository.TiketRepository
import kotlinx.coroutines.launch


class InsertTiketViewModel (
    private val tkt: TiketRepository,
    private val evnt: EventRepository,
    private val psrt: PesertaRepository,

): ViewModel() {
    var TiketuiState by mutableStateOf(InsertTiketUiState())
        private set
    var eventList by mutableStateOf<List<Event>>(emptyList())
        private set
    var pesertaList by mutableStateOf<List<Peserta>>(emptyList())
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init{
        viewModelScope.launch {
            loadEvent()
            loadPeserta()
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

    fun updateInsertTktState(insertTiketUiEvent: InsertTiketUiEvent) {
        TiketuiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    suspend fun insertTkt() {
        viewModelScope.launch {
            try {
                tkt.insertTiket(TiketuiState.insertTiketUiEvent.toTkt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
)

data class InsertTiketUiEvent(
    val id_tiket: Int = 0,
    val id_event: Int = 0,
    val id_peserta: Int = 0,
    val kapasitas_tiket: String = "",
    val harga_tiket: Int = 0,
)

fun InsertTiketUiEvent.toTkt(): Tiket = Tiket(
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket,
)

fun Tiket.toUiStateTkt():InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = toInsertUiEvent()
)

fun Tiket.toInsertUiEvent():InsertTiketUiEvent = InsertTiketUiEvent(
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)