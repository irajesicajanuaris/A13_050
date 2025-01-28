package com.example.finalprojectpam.ui.view.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventUiEvent
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventUiState
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventViewModel
import kotlinx.coroutines.launch

object DestinasiEntryEvent : DestinasiNavigasi {
    override val route = "item_entryEvent"
    override val titleRes = "Entry Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: InsertEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryEvent.titleRes,
                canNavigateBack = true,
                onBackClick = navigateBack,
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            BottomAppBarDefaults(
                navController = rememberNavController(),
                onEventClick = onEventClick,
                onPesertaClick = onPesertaClick,
                onTiketClick = onTiketClick,
                onTransaksiClick = onTransaksiClick
            )
        }
    ){
            innerPadding ->
        EntryBodyEvent(
            insertEventUiState = viewModel.EventuiState,
            onEventValueChange = viewModel::updateInsertEvntState,
            onSaveClick = {
                coroutineScope.launch{
                    viewModel.insertEvnt()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyEvent(
    insertEventUiState: InsertEventUiState,
    onEventValueChange: (InsertEventUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputEvent(
            insertEventUiEvent = insertEventUiState.insertEventUiEvent,
            onValueChange = onEventValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputEvent(
    insertEventUiEvent: InsertEventUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertEventUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertEventUiEvent.nama_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(nama_event = it))},
            label = { Text("Nama Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertEventUiEvent.deskripsi_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(deskripsi_event = it))},
            label = { Text("Deskripsi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertEventUiEvent.tanggal_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(tanggal_event = it))},
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertEventUiEvent.lokasi_event,
            onValueChange = {onValueChange(insertEventUiEvent.copy(lokasi_event = it))},
            label = { Text("Lokasi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if(enabled){
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}