
package com.example.finalprojectpam.ui.view.transaksi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
import com.example.finalprojectpam.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.finalprojectpam.ui.viewmodel.transaksi.DetailTransaksiViewModel


object DestinasiDetailTransaksi : DestinasiNavigasi {
    override val route = "detail_transaksi/{id_transaksi}"
    override val titleRes = "Detail Transaksi"
    const val Id_transaksi = "id_transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaksiScreen(
    onEditClick: (Int) -> Unit = { },
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: DetailTransaksiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    idTransaksi: Int,

    ) {
    val transaksi = viewModel.TransaksiuiState.detailTransaksiUiEvent

    LaunchedEffect(idTransaksi) {
        viewModel.fetchDetailTransaksi(idTransaksi)
    }
    val isLoading = viewModel.TransaksiuiState.isLoading
    val isError = viewModel.TransaksiuiState.isError
    val errorMessage = viewModel.TransaksiuiState.errorMessage
    val eventName = viewModel.TransaksiuiState.eventName
    val pesertaName = viewModel.TransaksiuiState.pesertaName
    val tanggalTransaksi = viewModel.TransaksiuiState.tanggalTransaksi

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Transaksi") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
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
                onClick = { onEditClick(transaksi.id_transaksi) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Transaksi")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.TransaksiuiState.isUiTransaksiNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Id Tiket: ${transaksi.id_tiket}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Event: $eventName",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Peserta: $pesertaName",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Tanggal Transaksi: $tanggalTransaksi",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Jumlah Pembayaran: ${transaksi.jumlah_pembayaran}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Jumlah Tiket: ${transaksi.jumlah_tiket}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
