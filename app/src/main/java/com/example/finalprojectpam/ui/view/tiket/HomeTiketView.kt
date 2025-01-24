package com.example.finalprojectpam.ui.view.tiket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalprojectpam.R
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.HomeTiketUiState
import com.example.finalprojectpam.ui.viewmodel.tiket.HomeTiketViewModel

object DestinasiHomeTiket : DestinasiNavigasi {
    override val route = "home_tiket"
    override val titleRes = "Home Tiket"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTiketScreen(
    navigateToItemEntryTiket:() -> Unit,
    modifier: Modifier = Modifier,
    ondetailClick: (Int) -> Unit = {},
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTiket.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTkt()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryTiket,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket")
            }
        },
    ) { innerPadding ->
        HomeTiketStatus(
            homeTiketUiState = viewModel.tktUIState,
            retryAction = { viewModel.getTkt() },
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            ondeleteClick = {
                it.id_tiket?.let { id ->
                    viewModel.deleteTkt(it.id_tiket)
                    viewModel.getTkt()
                }
            },
            ondetailClick = ondetailClick,
            events = viewModel.eventList,
            peserta = viewModel.pesertaList
        )
    }


}


@Composable
fun HomeTiketStatus(
    homeTiketUiState: HomeTiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    ondeleteClick: (Tiket) -> Unit,
    ondetailClick: (Int) -> Unit,
    events: List<Event>,
    peserta: List<Peserta>,
){
    when (homeTiketUiState){
        is HomeTiketUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTiketUiState.Success ->
            if (homeTiketUiState.tiket.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Tiket")
                }
            }else{
                TktLayout(
                    tiket = homeTiketUiState.tiket,
                    modifier = modifier.fillMaxWidth(),
                    ondetailClick = ondetailClick,
                    ondeleteClick = { ondeleteClick(it) },
                    events = events,
                    peserta = peserta
                )
            }
        is HomeTiketUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TktLayout(
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    ondetailClick: (Int) -> Unit,
    ondeleteClick: (Tiket) -> Unit = {},
    events: List<Event>,
    peserta: List<Peserta>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tiket) { tiketItem ->
            val event = events.find { it.id_event == tiketItem.id_event }
            val participant = peserta.find { it.id_peserta == tiketItem.id_peserta }

            event?.let { evnt ->
                participant?.let { psrt ->
                    TktCard(
                        tiket = tiketItem,
                        event = evnt,
                        peserta = psrt,
                        modifier = Modifier.fillMaxWidth(),
                        ondetailClick = ondetailClick,
                        ondeleteClick = { ondeleteClick(tiketItem) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TktCard(
    tiket: Tiket,
    event: Event,
    peserta: Peserta,
    modifier: Modifier = Modifier,
    ondeleteClick: (Tiket) -> Unit = {},
    ondetailClick: (Int) -> Unit
) {
    Card(
        onClick = { ondetailClick(tiket.id_tiket) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "ID Tiket: ${tiket.id_tiket}",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Event: ${event.nama_event}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Peserta: ${peserta.nama_peserta}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tanggal Event: ${event.tanggal_event}",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lokasi Event: ${event.lokasi_event}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { ondeleteClick(tiket) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}
