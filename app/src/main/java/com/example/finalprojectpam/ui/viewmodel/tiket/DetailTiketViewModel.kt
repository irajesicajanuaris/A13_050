package com.example.finalprojectpam.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.repository.TiketRepository
import kotlinx.coroutines.launch


class DetailTiketViewModel(private val tktRepository: TiketRepository) : ViewModel() {

    var TiketuiState by mutableStateOf(DetailTiketUiState())
        private set

    fun fetchDetailTiket(idTiket: Int) {
        viewModelScope.launch {
            TiketuiState = DetailTiketUiState(isLoading = true)
            try {
                val tiket = tktRepository.getTiketbyidTiket(idTiket)
                TiketuiState = DetailTiketUiState(detailTiketUiEvent = tiket.toDetailTiketUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                TiketuiState = DetailTiketUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailTiketUiState(
    val detailTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiTiketNotEmpty: Boolean
        get() = detailTiketUiEvent != InsertTiketUiEvent()
}

fun Tiket.toDetailTiketUiEvent(): InsertTiketUiEvent {
    return InsertTiketUiEvent(
        id_tiket = id_tiket,
        id_event = id_event,
        id_peserta = id_peserta,
        kapasitas_tiket = kapasitas_tiket.toString(),
        harga_tiket = harga_tiket
    )
}