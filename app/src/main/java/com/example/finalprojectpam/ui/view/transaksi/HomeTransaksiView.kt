package com.example.finalprojectpam.ui.view.transaksi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.R
import com.example.finalprojectpam.model.Event
import com.example.finalprojectpam.model.Peserta
import com.example.finalprojectpam.model.Tiket
import com.example.finalprojectpam.model.Transaksi
import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.HomeTransaksiUiState
import com.example.finalprojectpam.ui.viewmodel.transaksi.HomeTransaksiViewModel


object DestinasiHomeTransaksi : DestinasiNavigasi {
    override val route = "home_transaksi"
    override val titleRes = "Home Transaksi"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTransaksiScreen(
    navigateToItemEntryTransaksi:() -> Unit,
    modifier: Modifier = Modifier,
    ondetailClick: (Int) -> Unit = {},
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: HomeTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTransaksi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTrsksi()
                }
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryTransaksi,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaksi")
            }
        },
    ) { innerPadding ->
        HomeTransaksiStatus(
            homeTransaksiUiState = viewModel.trsksiUIState,
            retryAction = { viewModel.getTrsksi() },
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            ondeleteClick = {
                it.id_transaksi?.let { id ->
                    viewModel.deleteTrsksi(it.id_transaksi)
                    viewModel.getTrsksi()
                }
            },
            ondetailClick = ondetailClick
        )
    }
}


@Composable
fun HomeTransaksiStatus(
    homeTransaksiUiState: HomeTransaksiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    ondeleteClick: (Transaksi) -> Unit,
    ondetailClick: (Int) -> Unit,
){
    when (homeTransaksiUiState){
        is HomeTransaksiUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTransaksiUiState.Success -> {
            if (homeTransaksiUiState.transaksi.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Transaksi")
                }
            } else {
                TrsksiLayout(
                    transaksi = homeTransaksiUiState.transaksi,
                    modifier = modifier.fillMaxWidth(),
                    ondetailClick = ondetailClick,
                    ondeleteClick = { ondeleteClick(it) },
                    events = homeTransaksiUiState.events,
                    peserta = homeTransaksiUiState.peserta,
                    tiket = homeTransaksiUiState.tiket
                )
            }
        }
        is HomeTransaksiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun TrsksiLayout(
    transaksi: List<Transaksi>,
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    ondetailClick: (Int) -> Unit,
    ondeleteClick: (Transaksi) -> Unit = {},
    events: List<Event>,
    peserta: List<Peserta>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transaksi) { transaksiItem ->
            val tikets = tiket.find { it.id_tiket == transaksiItem.id_tiket }

            val event = tikets?.let { tiket ->
                events.find { it.id_event == tiket.id_event }
            }

            val peserta = tikets?.let { tiket ->
                peserta.find { it.id_peserta == tiket.id_peserta }
            }
            TrsksiCard(
                tiket = tikets,
                event = event,
                peserta = peserta,
                transaksi = transaksiItem,
                modifier = Modifier.fillMaxWidth(),
                ondeleteClick = { ondeleteClick(transaksiItem) },
                ondetailClick = ondetailClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrsksiCard(
    tiket: Tiket?,
    event: Event?,
    peserta: Peserta?,
    transaksi: Transaksi,
    modifier: Modifier = Modifier,
    ondeleteClick: (Transaksi) -> Unit = {},
    ondetailClick: (Int) -> Unit
) {
    Card(
        onClick = { ondetailClick(transaksi.id_transaksi) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "ID Transaksi: ${transaksi.id_transaksi}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "ID Tiket: ${tiket?.id_tiket?:"Tidak Ada"}",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Event: ${event?.nama_event?:"Tidak Ada"}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Peserta: ${peserta?.nama_peserta?:"Tidak Ada"}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Tanggal Transaksi: ${transaksi.tanggal_transaksi}",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Pembayaran: ${transaksi.jumlah_pembayaran}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { ondeleteClick(transaksi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


