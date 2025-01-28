package com.example.finalprojectpam.ui.view.tiket

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
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.UpdateTiketViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdateTiket: DestinasiNavigasi {
    override val route = "update_tiket"
    override val titleRes = "Update Tiket"
    const val IdTiket = "id_tiket"
    val routesWithArg = "$route/{$IdTiket}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTiket.titleRes,
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
        EntryBodyTiket(
            insertTiketUiState = viewModel.updateTiketUiState,
            onTiketValueChange = viewModel::updateInsertTktState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTkt()
                    delay(600)
                    withContext(Dispatchers.Main){
                        Log.d("UpdateTiket", "Data yang dikirim: ${viewModel.updateTiketUiState}")
                        onNavigate()
                    }
                }
            },
            modifier = Modifier.padding(padding),
            eventList = viewModel.eventList,
            pesertaList = viewModel.pesertaList
        )
    }
}