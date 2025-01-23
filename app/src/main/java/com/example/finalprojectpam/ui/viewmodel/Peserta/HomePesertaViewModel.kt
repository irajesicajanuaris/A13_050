package com.example.finalprojectpam.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException


sealed class HomePesertaUiState{
    data class Success(val peserta: List<Peserta>): HomePesertaUiState()
    object Error : HomePesertaUiState()
    object Loading : HomePesertaUiState()
}

class HomePesertaViewModel(private val psrt: PesertaRepository): ViewModel() {
    var psrtUIState: HomePesertaUiState by mutableStateOf(HomePesertaUiState.Loading)
        private set

    init {
        getPsrt()
    }

    fun getPsrt() {
        viewModelScope.launch {
            psrtUIState = HomePesertaUiState.Loading
            psrtUIState = try {
                HomePesertaUiState.Success(psrt.getPeserta().data)
            } catch (e: IOException) {
                HomePesertaUiState.Error
            } catch (e: HttpException) {
                HomePesertaUiState.Error
            }
        }
    }

    fun deletePsrt(id_peserta: Int) {
        viewModelScope.launch {
            try {
                psrt.deletePeserta(id_peserta)
            } catch (e: IOException) {
                HomePesertaUiState.Error
            } catch (e: HttpException) {
                HomePesertaUiState.Error
            }
        }
    }
}