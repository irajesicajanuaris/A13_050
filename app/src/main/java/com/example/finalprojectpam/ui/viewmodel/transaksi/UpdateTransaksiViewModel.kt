package com.example.finalprojectpam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.repository.TiketRepository
import com.example.finalprojectpam.repository.TransaksiRepository
import com.example.finalprojectpam.ui.view.transaksi.DestinasiUpdateTransaksi
import kotlinx.coroutines.launch


class UpdateTransaksiViewModel(
    savedStateHandle: SavedStateHandle,
    private val trsksi: TransaksiRepository,
    private val tkt: TiketRepository,
): ViewModel(){
    var updateTransaksiUiState by mutableStateOf(InsertTransaksiUiState())
        private set


    private val _idTransaksi: Int = checkNotNull(savedStateHandle[DestinasiUpdateTransaksi.IdTransaksi])
    var tiketList by mutableStateOf<List<Tiket>>(emptyList())
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set


    init {
        viewModelScope.launch {
            loadTransaksi()
            loadTiket()
        }
    }
    private suspend fun loadTiket() {
        try {
            tiketList = tkt.getAllTikets()
        } catch (e: Exception) {
            errorMessage = "Failed to load tiket data"
            e.printStackTrace()
        }
    }
    private suspend fun loadTransaksi() {
        try {
            val transaksi = trsksi.getTransaksibyidTransaksi(_idTransaksi)
            updateTransaksiUiState = transaksi.toUiStateTrsksi()
        } catch (e: Exception) {
            errorMessage = "Failed to load transaksi data"
            e.printStackTrace()
        }
    }


    fun updateInsertTrsksiState(insertTransaksiUiEvent: InsertTransaksiUiEvent){
        updateTransaksiUiState = InsertTransaksiUiState(insertTransaksiUiEvent = insertTransaksiUiEvent)
    }

    suspend fun updateTrsksi() {
        viewModelScope.launch {
            try {
                trsksi.updateTransaksi(_idTransaksi, updateTransaksiUiState.insertTransaksiUiEvent.toTrsksi())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
