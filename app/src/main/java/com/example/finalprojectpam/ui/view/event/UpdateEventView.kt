package com.example.finalprojectpam.ui.view.event

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
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.event.UpdateEventViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdateEvent: DestinasiNavigasi {
    override val route = "update_event"
    override val titleRes = "Update Event"
    const val IdEvent = "id_event"
    val routesWithArg = "$route/{$IdEvent}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEventScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBodyEvent(
            modifier = Modifier.padding(padding),
            insertEventUiState = viewModel.updateEventUiState,
            onEventValueChange = viewModel::updateInsertEvntState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEvnt()
                    delay(600)
                    withContext(Dispatchers.Main){
                        Log.d("UpdateEvent", "Data yang dikirim: ${viewModel.updateEventUiState}")
                        onNavigate()
                    }
                }
            }
        )
    }
}