package com.example.finalprojectpam.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.model.Transaksi
import com.example.finalprojectpam.repository.EventRepository
import com.example.finalprojectpam.repository.PesertaRepository
import com.example.finalprojectpam.repository.TiketRepository
import com.example.finalprojectpam.repository.TransaksiRepository
import kotlinx.coroutines.launch


class InsertTransaksiViewModel(
    private val trsksi: TransaksiRepository,
    private val tkt: TiketRepository,
    private val evnt: EventRepository,
    private val psrt: PesertaRepository,

) : ViewModel() {
    var TransaksiuiState by mutableStateOf(InsertTransaksiUiState())
        private set
    var tiketList by mutableStateOf<List<Tiket>>(emptyList())
        private set
    var pesertaList by mutableStateOf<List<Peserta>>(emptyList())
        private set
    var eventList by mutableStateOf<List<Event>>(emptyList())
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set


    init {
        viewModelScope.launch {
            loadTiket()
            loadEvent()
            loadPeserta()
        }
    }

    private suspend fun loadTiket() {
        tiketList = tkt.getAllTikets()
        tiketList.forEach() { tiket ->
            println("Tiket: ${tiket.id_tiket}")
        }
    }

    private suspend fun loadEvent() {
        eventList = evnt.getAllEvents()
        eventList.forEach() { event ->
            println("Event: ${event.id_event}")
        }
    }

    private suspend fun loadPeserta() {
        pesertaList = psrt.getAllPesertas()
        pesertaList.forEach() { peserta ->
            println("Peserta: ${peserta.id_peserta}")
        }
    }


    fun updateInsertTransaksiState(insertTransaksiUiEvent: InsertTransaksiUiEvent) {
        val selectedTiket = tiketList.find { it.id_tiket == insertTransaksiUiEvent.id_tiket }
        val hargaTiket = selectedTiket?.harga_tiket ?: 0
        val jumlahTiket = insertTransaksiUiEvent.jumlah_tiket
        val totalHarga = jumlahTiket * hargaTiket

        TransaksiuiState = InsertTransaksiUiState(insertTransaksiUiEvent = insertTransaksiUiEvent)

        TransaksiuiState = TransaksiuiState.copy(
            insertTransaksiUiEvent = insertTransaksiUiEvent.copy(
                jumlah_pembayaran = totalHarga.toString()
            )
        )
    }


    fun insertTrsksi() {
        viewModelScope.launch {
            try {
                trsksi.insertTransaksi(TransaksiuiState.insertTransaksiUiEvent.toTrsksi())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTransaksiUiState(
    val insertTransaksiUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent()
)

data class InsertTransaksiUiEvent(
    val id_transaksi: Int = 0,
    val id_tiket: Int = 0,
    val jumlah_tiket: Int = 0,
    val jumlah_pembayaran: String = "",
    val tanggal_transaksi: String = "",
    val id_event: Int = 0,
    val id_peserta: Int = 0,

    )

fun InsertTransaksiUiEvent.toTrsksi(): Transaksi = Transaksi(
    id_transaksi = id_transaksi,
    id_tiket = id_tiket,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi,

)

fun Transaksi.toUiStateTrsksi():InsertTransaksiUiState = InsertTransaksiUiState(
    insertTransaksiUiEvent = toInsertUiEvent()
)

fun Transaksi.toInsertUiEvent():InsertTransaksiUiEvent = InsertTransaksiUiEvent(
    id_transaksi = id_transaksi,
    id_tiket = id_tiket,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi,
)