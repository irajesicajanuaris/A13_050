package com.example.finalprojectpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalprojectpam.EventApplications
import com.example.finalprojectpam.ui.viewmodel.Peserta.DetailPesertaViewModel
import com.example.finalprojectpam.ui.viewmodel.Peserta.HomePesertaViewModel
import com.example.finalprojectpam.ui.viewmodel.Peserta.InsertPesertaViewModel
import com.example.finalprojectpam.ui.viewmodel.Peserta.UpdatePesertaViewModel
import com.example.finalprojectpam.ui.viewmodel.event.DetailEventViewModel
import com.example.finalprojectpam.ui.viewmodel.event.HomeEventViewModel
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventViewModel
import com.example.finalprojectpam.ui.viewmodel.event.UpdateEventViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.DetailTiketViewModel
//import com.example.finalprojectpam.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.UpdateTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.DetailTransaksiViewModel
//import com.example.finalprojectpam.ui.viewmodel.tiket.UpdateTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.HomeTransaksiViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.InsertTransaksiViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.UpdateTransaksiViewModel


object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeEventViewModel(aplikasiEvent().container.eventRepository) }
        initializer { InsertEventViewModel(aplikasiEvent().container.eventRepository) }
        initializer { DetailEventViewModel(aplikasiEvent().container.eventRepository) }
        initializer { UpdateEventViewModel(createSavedStateHandle(),aplikasiEvent().container.eventRepository) }

        initializer { HomePesertaViewModel(aplikasiEvent().container.pesertaRepository) }
        initializer { InsertPesertaViewModel(aplikasiEvent().container.pesertaRepository) }
        initializer { DetailPesertaViewModel(aplikasiEvent().container.pesertaRepository) }
        initializer { UpdatePesertaViewModel(createSavedStateHandle(),aplikasiEvent().container.pesertaRepository) }

        initializer { HomeTiketViewModel(aplikasiEvent().container.tiketRepository,aplikasiEvent().container.eventRepository,aplikasiEvent().container.pesertaRepository) }
        initializer { InsertTiketViewModel(aplikasiEvent().container.tiketRepository,aplikasiEvent().container.eventRepository,aplikasiEvent().container.pesertaRepository) }
        initializer { DetailTiketViewModel(aplikasiEvent().container.tiketRepository) }
        initializer { UpdateTiketViewModel(createSavedStateHandle(),aplikasiEvent().container.tiketRepository,aplikasiEvent().container.eventRepository,aplikasiEvent().container.pesertaRepository) }

        initializer { HomeTransaksiViewModel(aplikasiEvent().container.transaksiRepository,aplikasiEvent().container.tiketRepository,aplikasiEvent().container.eventRepository,aplikasiEvent().container.pesertaRepository) }
        initializer { InsertTransaksiViewModel(aplikasiEvent().container.transaksiRepository, aplikasiEvent().container.tiketRepository,aplikasiEvent().container.eventRepository,aplikasiEvent().container.pesertaRepository) }
        initializer { DetailTransaksiViewModel(aplikasiEvent().container.transaksiRepository,aplikasiEvent().container.eventRepository,aplikasiEvent().container.pesertaRepository) }
        initializer { UpdateTransaksiViewModel(createSavedStateHandle(),aplikasiEvent().container.transaksiRepository,aplikasiEvent().container.tiketRepository) }


    }
}
fun CreationExtras.aplikasiEvent(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)