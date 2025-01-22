package com.example.finalprojectpam.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalprojectpam.ui.view.event.DestinasiDetailEvent
import com.example.finalprojectpam.ui.view.event.DestinasiEntryEvent
import com.example.finalprojectpam.ui.view.event.DestinasiHomeEvent
import com.example.finalprojectpam.ui.view.event.DestinasiUpdateEvent
import com.example.finalprojectpam.ui.view.event.DetailEventScreen
import com.example.finalprojectpam.ui.view.event.EntryEventScreen
import com.example.finalprojectpam.ui.view.event.HomeEventScreen
import com.example.finalprojectpam.ui.view.event.UpdateEventScreen
import com.example.finalprojectpam.ui.view.home.DestinasiHome
import com.example.finalprojectpam.ui.view.home.MainHomeScreen
import com.example.finalprojectpam.ui.view.peserta.DestinasiEntryPeserta
import com.example.finalprojectpam.ui.view.peserta.DestinasiHomePeserta
import com.example.finalprojectpam.ui.view.peserta.HomePesertaScreen


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController(), modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route) {
            MainHomeScreen(
                navigateToEventScreen = { navController.navigate(DestinasiHomeEvent.route) },
                navigateToPesertaScreen = { navController.navigate(DestinasiHomePeserta.route) },
//               navigateToTiketScreen = { navController.navigate(DestinasiTiket.route) },
//               navigateToTransaksiScreen = { navController.navigate(DestinasiTransaksi.route) }
            )
        }
        composable(DestinasiHomeEvent.route) {
            HomeEventScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryEvent.route) },
                ondetailClick = { id_event ->
                    navController.navigate("detail/$id_event")
                    Log.d("NavigationDebug", "Navigating to detail/$id_event")

                }
            )
        }
        composable(DestinasiEntryEvent.route) {
            EntryEventScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeEvent.route) {
                        popUpTo(DestinasiHomeEvent.route) {
                            inclusive = true
                        }
                    }
                })
        }
        composable(
            route = "detail/{id_event}",
            arguments = listOf(
                navArgument("id_event") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_event = backStackEntry.arguments?.getInt(DestinasiDetailEvent.Id_event)
            id_event?.let {
                DetailEventScreen(
                    idEvent = it,
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateEvent.route}/$id_event")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            route = "${DestinasiUpdateEvent.route}/{id_event}",
            arguments = listOf(
                navArgument("id_event") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_event = backStackEntry.arguments?.getInt("id_event")
            id_event?.let {
                UpdateEventScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeEvent.route) {
                            popUpTo(DestinasiHomeEvent.route) { inclusive = true }
                        }
                    },
                    modifier = modifier,
                )
            }

        }
        composable(DestinasiHomePeserta.route) {
            HomePesertaScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPeserta.route) },
                ondetailClick = { id_peserta ->
                    navController.navigate("detail/$id_peserta")
                    Log.d("NavigationDebug", "Navigating to detail/$id_peserta")

                }
            )
        }
    }
}
