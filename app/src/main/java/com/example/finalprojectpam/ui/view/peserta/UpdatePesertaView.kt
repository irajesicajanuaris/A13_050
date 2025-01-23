package com.example.finalprojectpam.ui.view.peserta

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
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.view.event.EntryBodyEvent
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.Peserta.UpdatePesertaViewModel
import com.example.finalprojectpam.ui.viewmodel.event.UpdateEventViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdatePeserta: DestinasiNavigasi {
    override val route = "update_peserta"
    override val titleRes = "Update Peserta"
    const val IdPeserta = "id_peserta"
    val routesWithArg = "$route/{$IdPeserta}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePesertaScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdatePesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePeserta.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodyPeserta(
            modifier = Modifier.padding(padding),
            insertPesertaUiState = viewModel.updatePesertaUiState,
            onEventValueChange = viewModel::updateInsertPsrtState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePsrt()
                    delay(600)
                    withContext(Dispatchers.Main){
                        Log.d("UpdatePeserta", "Data yang dikirim: ${viewModel.updatePesertaUiState}")
                        onNavigate()
                    }
                }
            }
        )
    }
}