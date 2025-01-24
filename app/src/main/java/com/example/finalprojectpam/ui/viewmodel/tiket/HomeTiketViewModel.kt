package com.example.finalprojectpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.repository.TiketRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeTiketUiState{
    data class Success(val tiket: List<Tiket>): HomeTiketUiState()
    object Error : HomeTiketUiState()
    object Loading : HomeTiketUiState()
}

class HomeTiketViewModel(
    private val tktRepository: TiketRepository,
    private val evntRepository: EventRepository,
    private val pesertaRepository: PesertaRepository
) : ViewModel() {
    var tktUIState: HomeTiketUiState by mutableStateOf(HomeTiketUiState.Loading)
        private set
    var eventList by mutableStateOf<List<Event>>(emptyList())
        private set
    var pesertaList by mutableStateOf<List<Peserta>>(emptyList())
        private set

    init {
        getTkt()
    }

    fun getTkt() {
        viewModelScope.launch {
            tktUIState = HomeTiketUiState.Loading
            try {
                val tiketData = tktRepository.getTiket().data
                eventList = evntRepository.getAllEvents()
                pesertaList = pesertaRepository.getAllPesertas()
                tktUIState = HomeTiketUiState.Success(tiketData)
            } catch (e: Exception) {
                tktUIState = HomeTiketUiState.Error
            }
        }
    }

    fun deleteTkt(id_tiket: Int) {
        viewModelScope.launch {
            try {
                tktRepository.deleteTiket(id_tiket)
            } catch (e: IOException) {
                HomeTiketUiState.Error
            } catch (e: HttpException) {
                HomeTiketUiState.Error
            }
        }
    }
}