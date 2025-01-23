package com.example.finalprojectpam.ui.viewmodel.Peserta

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.ui.view.peserta.DestinasiUpdatePeserta
import com.example.finalprojectpam.ui.viewmodel.event.toEvnt
import kotlinx.coroutines.launch


class UpdatePesertaViewModel(
    savedStateHandle: SavedStateHandle,
    private val pesertaRepository: PesertaRepository
): ViewModel(){
    var updatePesertaUiState by mutableStateOf(InsertPesertaUiState())
        private set

    private val _idPeserta: Int = checkNotNull(savedStateHandle[DestinasiUpdatePeserta.IdPeserta])

    init {
        viewModelScope.launch {
            updatePesertaUiState = pesertaRepository.getPesertabyidPeserta(_idPeserta)
                .toUiStatePsrt()
        }
    }

    fun updateInsertPsrtState(insertPesertaUiEvent: InsertPesertaUiEvent){
        updatePesertaUiState = InsertPesertaUiState(insertPesertaUiEvent = insertPesertaUiEvent)
    }

    suspend fun updatePsrt() {
        viewModelScope.launch {
            try {
                pesertaRepository.updatePeserta(_idPeserta, updatePesertaUiState.insertPesertaUiEvent.toPsrt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
