package com.example.finalprojectpam.ui.viewmodel.transaksi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.model.Transaksi
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.repository.TiketRepository
import com.example.finalprojectpam.repository.TransaksiRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomeTransaksiUiState{
    data class Success(val transaksi: List<Transaksi>, val tiket: List<Tiket>, val events: List<Event>, val peserta: List<Peserta>): HomeTransaksiUiState()
    object Error : HomeTransaksiUiState()
    object Loading : HomeTransaksiUiState()
}

class HomeTransaksiViewModel(
    private val trsksiRepository: TransaksiRepository,
    private val tktRepository: TiketRepository,
    private val evntRepository: EventRepository,
    private val pesertaRepository: PesertaRepository
): ViewModel() {
    var trsksiUIState: HomeTransaksiUiState by mutableStateOf(HomeTransaksiUiState.Loading)
        private set

    init {
        getTrsksi()
    }

    fun getTrsksi() {
        viewModelScope.launch {
            trsksiUIState = HomeTransaksiUiState.Loading
            try {
                val transaksiData = trsksiRepository.getTransaksi().data
                val tiket = tktRepository.getTiket().data
                val events = evntRepository.getEvent().data
                val peserta = pesertaRepository.getPeserta().data
                trsksiUIState = HomeTransaksiUiState.Success(transaksiData, tiket, events, peserta)
            } catch (e: Exception) {
                e.printStackTrace()
                trsksiUIState = HomeTransaksiUiState.Error
                Log.e("HomeTransaksiViewModel", "Error: ${e.message}")
            }
        }
    }

    fun deleteTrsksi(id_transaksi: Int) {
        viewModelScope.launch {
            try {
                trsksiRepository.deleteTransaksi(id_transaksi)
            } catch (e: IOException) {
                HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                HomeTransaksiUiState.Error
            }
        }
    }
}
