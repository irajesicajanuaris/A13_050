package com.example.finalprojectpam.ui.viewmodel.Peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.repository.PesertaRepository
import kotlinx.coroutines.launch


class InsertPesertaViewModel (private val psrt: PesertaRepository): ViewModel(){
    var PesertauiState by mutableStateOf(InsertPesertaUiState())
        private set

    fun updateInsertPsrtState(insertPesertaUiEvent: InsertPesertaUiEvent){
        PesertauiState = InsertPesertaUiState(insertPesertaUiEvent = insertPesertaUiEvent)
    }

    suspend fun insertPsrt(){
        viewModelScope.launch {
            try {
                psrt.insertPeserta(PesertauiState.insertPesertaUiEvent.toPsrt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPesertaUiState(
    val insertPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent()
    
)

data class InsertPesertaUiEvent(
    val id_peserta: Int = 0,
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)

fun InsertPesertaUiEvent.toPsrt(): Peserta = Peserta(
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toUiStatePsrt():InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaUiEvent = toInsertUiEvent()
)

fun Peserta.toInsertUiEvent():InsertPesertaUiEvent = InsertPesertaUiEvent(
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)