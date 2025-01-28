package com.example.finalprojectpam.ui.view.tiket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.Peserta.InsertPesertaUiState
import com.example.finalprojectpam.ui.viewmodel.event.InsertEventUiState
import com.example.finalprojectpam.ui.viewmodel.tiket.InsertTiketUiEvent
import com.example.finalprojectpam.ui.viewmodel.tiket.InsertTiketUiState
import com.example.finalprojectpam.ui.viewmodel.tiket.InsertTiketViewModel
import kotlinx.coroutines.launch


object DestinasiEntryTiket : DestinasiNavigasi {
    override val route = "entry_tiket"
    override val titleRes = "Entry Tiket"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = navigateBack
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
        EntryBodyTiket(
            insertTiketUiState = viewModel.TiketuiState,
            eventList = viewModel.eventList,
            pesertaList = viewModel.pesertaList,
            onTiketValueChange = viewModel::updateInsertTktState,
            onSaveClick = {
                coroutineScope.launch{
                    viewModel.insertTkt()
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
fun EntryBodyTiket(
    insertTiketUiState: InsertTiketUiState,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    pesertaList: List<Peserta>,
    eventList: List<Event>,
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputTiket(
            insertTiketUiEvent = insertTiketUiState.insertTiketUiEvent,
            eventList = eventList,
            pesertaList = pesertaList,
            onValueChange = onTiketValueChange,
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
fun FormInputTiket(
    insertTiketUiEvent: InsertTiketUiEvent,
    eventList: List<Event>,
    pesertaList: List<Peserta>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTiketUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        EventDropDown(
            eventList = eventList,
            selectedEventId = insertTiketUiEvent.id_event,
            onEventSelected = { selectedEventId ->
                onValueChange(insertTiketUiEvent.copy(id_event = selectedEventId))
            }
        )
        PesertaDropDown(
            pesertaList = pesertaList,
            selectedPesertaId = insertTiketUiEvent.id_peserta,
            onPesertaSelected = { selectedPesertaId ->
                onValueChange(insertTiketUiEvent.copy(id_peserta = selectedPesertaId))
            }
        )
        OutlinedTextField(
            value = insertTiketUiEvent.kapasitas_tiket,
            onValueChange = {onValueChange(insertTiketUiEvent.copy(kapasitas_tiket = it))},
            label = { Text("Kapasitas Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTiketUiEvent.harga_tiket.toString(),
            onValueChange = {onValueChange(insertTiketUiEvent.copy(harga_tiket = it.toInt()))},
            label = { Text("Harga Tiket") },
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDropDown(
    eventList: List<Event>,
    selectedEventId: Int?,
    onEventSelected: (Int) -> Unit
){
    val options = eventList.map{it.id_event.toString()}
    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember { mutableStateOf(selectedEventId?.toString()?: options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {expanded.value = !expanded.value}
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Pilih Id Event")},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {expanded.value = false}
        ){
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption)},
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onEventSelected(selectionOption.toInt())
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PesertaDropDown(
    pesertaList: List<Peserta>,
    selectedPesertaId: Int?,
    onPesertaSelected: (Int) -> Unit
){
    val options = pesertaList.map{it.id_peserta.toString()}
    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember { mutableStateOf(selectedPesertaId?.toString()?: options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {expanded.value = !expanded.value}
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Pilih Id Peserta")},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {expanded.value = false}
        ){
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption)},
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onPesertaSelected(selectionOption.toInt())
                    }
                )
            }
        }
    }
}

