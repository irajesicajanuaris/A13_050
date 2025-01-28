package com.example.finalprojectpam.ui.view.transaksi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.InsertTransaksiUiEvent
import com.example.finalprojectpam.ui.viewmodel.transaksi.InsertTransaksiUiState
import com.example.finalprojectpam.ui.viewmodel.transaksi.InsertTransaksiViewModel
import kotlinx.coroutines.launch


object DestinasiEntryTransaksi : DestinasiNavigasi {
    override val route = "entry_transaksi"
    override val titleRes = "Entry Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTransaksiScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: InsertTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTransaksi.titleRes,
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
        EntryBodyTransaksi(
            insertTransaksiUiState = viewModel.TransaksiuiState,
            tiketList = viewModel.tiketList,
            onTransaksiValueChange = viewModel::updateInsertTransaksiState,
            onSaveClick = {
                coroutineScope.launch{
                    viewModel.insertTrsksi()
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
fun EntryBodyTransaksi(
    insertTransaksiUiState: InsertTransaksiUiState,
    onTransaksiValueChange: (InsertTransaksiUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier,
    tiketList: List<Tiket>,
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)

    ){
        FormInputTransaksi(
            insertTransaksiUiEvent = insertTransaksiUiState.insertTransaksiUiEvent,
            onValueChange = onTransaksiValueChange,
            tiketList = tiketList,
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
fun FormInputTransaksi(
    insertTransaksiUiEvent: InsertTransaksiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTransaksiUiEvent) -> Unit = {},
    tiketList: List<Tiket>,
    enabled: Boolean = true
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        TiketDropDown(
            tiketList = tiketList,
            selectedTiketId = insertTransaksiUiEvent.id_tiket,
            onTiketSelected = { selectedTiketId ->
                onValueChange(insertTransaksiUiEvent.copy(id_tiket = selectedTiketId))
            }
        )
        OutlinedTextField(
            value = insertTransaksiUiEvent.jumlah_tiket.toString(),
            onValueChange = {
                val selectedTiket = tiketList.find { it.id_tiket == insertTransaksiUiEvent.id_tiket }
                val hargaTiket = selectedTiket?.harga_tiket ?: 0
                val jumlahTiket = it.toIntOrNull() ?: 0
                val totalHarga = jumlahTiket * hargaTiket
                onValueChange(insertTransaksiUiEvent.copy(jumlah_tiket = jumlahTiket, jumlah_pembayaran = totalHarga.toString()))
            },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTransaksiUiEvent.jumlah_pembayaran,
            onValueChange = {onValueChange(insertTransaksiUiEvent.copy(jumlah_pembayaran = it))},
            label = { Text("Jumlah Pembayaran") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTransaksiUiEvent.tanggal_transaksi,
            onValueChange = {onValueChange(insertTransaksiUiEvent.copy(tanggal_transaksi = it))},
            label = { Text("Tanggal Transaksi") },
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
fun TiketDropDown(
    tiketList: List<Tiket>,
    selectedTiketId: Int?,
    onTiketSelected: (Int) -> Unit
){
    val options = tiketList.map{it.id_tiket.toString()}
    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember { mutableStateOf(selectedTiketId?.toString()?: options.getOrNull(0)) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = {expanded.value = !expanded.value}
    ) {
        OutlinedTextField(
            value = currentSelection.value ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Id Tiket")},
            trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)},
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
                        onTiketSelected(selectionOption.toInt())
                    }
                )
            }
        }
    }
}