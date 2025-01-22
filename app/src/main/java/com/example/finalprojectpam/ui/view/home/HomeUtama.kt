package com.example.finalprojectpam.ui.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalprojectpam.ui.navigation.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Utama"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHomeScreen(
    modifier: Modifier = Modifier,
    navigateToEventScreen: () -> Unit,
    navigateToPesertaScreen: () -> Unit,
//    navigateToTiketScreen: () -> Unit,
//    navigateToTransaksiScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Halaman Utama") }
            )
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 kolom
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        IconButtonWithText(
                            icon = Icons.Default.Home,
                            label = "Event",
                            onClick = navigateToEventScreen
                        )
                    }
                    item {
                        IconButtonWithText(
                            icon = Icons.Default.Person,
                            label = "Peserta",
                            onClick = navigateToPesertaScreen
                        )
                    }
                    item {
                        IconButtonWithText(
                            icon = Icons.Default.ShoppingCart,
                            label = "Tiket",
                            onClick = {}
                        )
                    }
                    item {
                        IconButtonWithText(
                            icon = Icons.Default.CheckCircle,
                            label = "Transaksi",
                            onClick = {}
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun IconButtonWithText(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}
