@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.finalprojectpam.ui.view.event

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
    import androidx.compose.material3.BottomAppBarDefaults
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
    import com.example.finalprojectpam.ui.costumewidget.BottomAppBarDefaults
    import com.example.finalprojectpam.ui.costumewidget.CostumeTopAppBar
    import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi
    import com.example.finalprojectpam.ui.viewmodel.PenyediaViewModel
    import com.example.finalprojectpam.ui.viewmodel.event.HomeEventUiState
    import com.example.finalprojectpam.ui.viewmodel.event.HomeEventViewModel


    object DestinasiHomeEvent : DestinasiNavigasi {
        override val route = "home_event"
        override val titleRes = "Home Event"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeEventScreen(
        navigateToItemEntry:() -> Unit,
        modifier: Modifier = Modifier,
        ondetailClick: (Int) -> Unit = {},
        onEventClick: () -> Unit,
        onPesertaClick: () -> Unit,
        onTiketClick: () -> Unit,
        onTransaksiClick: () -> Unit,
        viewModel: HomeEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ){
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold (
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CostumeTopAppBar(
                    title = DestinasiHomeEvent.titleRes,
                    canNavigateBack = true,
                    scrollBehavior = scrollBehavior,
                    onRefresh = {
                        viewModel.getEvnt()
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
                    onClick = navigateToItemEntry,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(18.dp)
                ){
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Event")
                }
            },
        ) { innerPadding ->
            HomeEventStatus(
                homeEventUiState = viewModel.evntUIState,
                retryAction = { viewModel.getEvnt() },
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                ondetailClick = ondetailClick,
                ondeleteClick = {
                    it.id_event?.let { id ->
                        viewModel.deleteEvnt(it.id_event)
                        viewModel.getEvnt()
                    }
                }
            )
        }
    }

    @Composable
    fun HomeEventStatus(
        homeEventUiState: HomeEventUiState,
        retryAction: () -> Unit,
        modifier: Modifier = Modifier,
        ondeleteClick: (Event) -> Unit,
        ondetailClick: (Int) -> Unit
    ){
        when (homeEventUiState){
            is HomeEventUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

            is HomeEventUiState.Success ->
                if (homeEventUiState.event.isEmpty()){
                    return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "Tidak ada data Event")
                    }
                }else{
                    EvntLayout(
                        event = homeEventUiState.event,
                        modifier = modifier.fillMaxWidth(),
                        ondetailClick = ondetailClick,
                        ondeleteClick = {
                            ondeleteClick(it) }
                    )
                }
            is HomeEventUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
    fun EvntLayout(
        event: List<Event>,
        modifier: Modifier = Modifier,
        ondetailClick: (Int) -> Unit,
        ondeleteClick: (Event) -> Unit = {}
    ){
        LazyColumn (
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            items(event) { eventItem ->
                EvntCard(
                    event = eventItem,
                    modifier = Modifier.fillMaxWidth(),
                    ondetailClick = ondetailClick,
                    ondeleteClick = { ondeleteClick(eventItem) }
                )
            }
        }
    }

    @Composable
    fun EvntCard(
        event: Event,
        modifier: Modifier = Modifier,
        ondeleteClick: (Event) -> Unit = {},
        ondetailClick: (Int) -> Unit
    ){
        Card (
            onClick = {ondetailClick(event.id_event)},
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ){
            Column (
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.nama_event,
                        style =   MaterialTheme.typography.titleLarge,
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { ondeleteClick(event) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                }

                Text(
                    text = event.deskripsi_event,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = event.tanggal_event,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = event.lokasi_event,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }