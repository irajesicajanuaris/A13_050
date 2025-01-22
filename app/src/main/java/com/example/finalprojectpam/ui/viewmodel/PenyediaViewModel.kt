package com.example.finalprojectpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalprojectpam.EventApplications
import com.example.finalprojectpam.ui.view.event.DetailEventScreen
import com.example.finalprojectpam.ui.viewmodel.Peserta.HomePesertaViewModel
import com.example.finalprojectpam.ui.viewmodel.event.DetailEventViewModel
import com.example.finalprojectpam.ui.viewmodel.event.HomeEventViewModel
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventViewModel
import com.example.finalprojectpam.ui.viewmodel.event.UpdateEventViewModel


object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeEventViewModel(aplikasiEvent().container.eventRepository) }
        initializer { InsertEventViewModel(aplikasiEvent().container.eventRepository) }
        initializer { DetailEventViewModel(aplikasiEvent().container.eventRepository) }
        initializer { UpdateEventViewModel(createSavedStateHandle(),aplikasiEvent().container.eventRepository) }
        initializer { HomePesertaViewModel(aplikasiEvent().container.pesertaRepository) }
    }
}
fun CreationExtras.aplikasiEvent(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)