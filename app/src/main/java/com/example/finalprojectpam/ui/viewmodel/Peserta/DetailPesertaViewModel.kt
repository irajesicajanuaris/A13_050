package com.example.finalprojectpam.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventUiEvent
import kotlinx.coroutines.launch


class DetailPesertaViewModel(private val psrtRepository: PesertaRepository) : ViewModel() {

    var PesertauiState by mutableStateOf(DetailPesertaUiState())
        private set

    fun fetchDetailPeserta(idPeserta: Int) {
        viewModelScope.launch {
            PesertauiState = DetailPesertaUiState(isLoading = true)
            try {
                val event = psrtRepository.getPesertabyidPeserta(idPeserta)
                PesertauiState = DetailPesertaUiState(detailPesertaUiEvent = event.toDetailPesertaUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                PesertauiState = DetailPesertaUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailPesertaUiState(
    val detailPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiPesertaNotEmpty: Boolean
        get() = detailPesertaUiEvent != InsertPesertaUiEvent()
}

fun Peserta.toDetailPesertaUiEvent(): InsertPesertaUiEvent {
    return InsertPesertaUiEvent(
        id_peserta= id_peserta,
        nama_peserta = nama_peserta,
        email = email,
        nomor_telepon = nomor_telepon
    )
}