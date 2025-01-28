package com.example.finalprojectpam.ui.view.transaksi

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.view.tiket.DestinasiUpdateTiket
import com.example.finalprojectpam.ui.view.tiket.EntryBodyTiket
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.UpdateTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.UpdateTransaksiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdateTransaksi: DestinasiNavigasi {
    override val route = "update_transaksi"
    override val titleRes = "Update Transaksi"
    const val IdTransaksi = "id_transaksi"
    val routesWithArg = "$route/{$IdTransaksi}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTransaksiScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: UpdateTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTransaksi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = onBack
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
    ){padding ->
        EntryBodyTransaksi(
            insertTransaksiUiState = viewModel.updateTransaksiUiState,
            onTransaksiValueChange = viewModel::updateInsertTrsksiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTrsksi()
                    delay(600)
                    withContext(Dispatchers.Main){
                        Log.d("UpdateTransaksi", "Data yang dikirim: ${viewModel.updateTransaksiUiState}")
                        onNavigate()
                    }
                }
            },
            modifier = Modifier.padding(padding),
            tiketList = viewModel.tiketList,

        )
    }
}