package com.example.finalprojectpam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Transaksi
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.repository.TransaksiRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailTransaksiViewModel(
    private val trsksiRepository: TransaksiRepository,
) : ViewModel() {

    var TransaksiuiState by mutableStateOf(DetailTransaksiUiState())
        private set

    fun fetchDetailTransaksi(idTransaksi: Int) {
        viewModelScope.launch {
            TransaksiuiState = DetailTransaksiUiState(isLoading = true)
            try {

                val transaksi = trsksiRepository.getTransaksibyidTransaksi(idTransaksi)

                TransaksiuiState = DetailTransaksiUiState(
                    detailTransaksiUiEvent = transaksi.toDetailTransaksiUiEvent(),
                    tanggalTransaksi = transaksi.tanggal_transaksi
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                TransaksiuiState = DetailTransaksiUiState(
                    isError = true,
                )
            } catch (e: Exception) {
                e.printStackTrace()
                TransaksiuiState = DetailTransaksiUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }



    data class DetailTransaksiUiState(
        val detailTransaksiUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = "",
        val eventName: String = "",
        val pesertaName: String = "",
        val tanggalTransaksi: String = "",
    ) {
        val isUiTransaksiNotEmpty: Boolean
            get() = detailTransaksiUiEvent != InsertTransaksiUiEvent()
    }

    fun Transaksi.toDetailTransaksiUiEvent(): InsertTransaksiUiEvent {
        return InsertTransaksiUiEvent(
            id_transaksi = id_transaksi,
            id_tiket = id_tiket,
            jumlah_tiket = jumlah_tiket,
            jumlah_pembayaran = jumlah_pembayaran,
            tanggal_transaksi = tanggal_transaksi
        )
    }
}