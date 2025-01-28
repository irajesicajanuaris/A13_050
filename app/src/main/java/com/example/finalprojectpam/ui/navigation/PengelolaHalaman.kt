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
import com.example.finalprojectpam.ui.view.peserta.DestinasiDetailPeserta
import com.example.finalprojectpam.ui.view.peserta.DestinasiEntryPeserta
import com.example.finalprojectpam.ui.view.peserta.DestinasiHomePeserta
import com.example.finalprojectpam.ui.view.peserta.DestinasiUpdatePeserta
import com.example.finalprojectpam.ui.view.peserta.DetailPesertaScreen
import com.example.finalprojectpam.ui.view.peserta.EntryPesertaScreen
import com.example.finalprojectpam.ui.view.peserta.HomePesertaScreen
import com.example.finalprojectpam.ui.view.peserta.UpdatePesertaScreen
import com.example.finalprojectpam.ui.view.tiket.DestinasiDetailTiket
import com.example.finalprojectpam.ui.view.tiket.DestinasiEntryTiket
import com.example.finalprojectpam.ui.view.tiket.DestinasiHomeTiket
import com.example.finalprojectpam.ui.view.tiket.DestinasiUpdateTiket
import com.example.finalprojectpam.ui.view.tiket.DetailTiketScreen
import com.example.finalprojectpam.ui.view.tiket.EntryTiketScreen
import com.example.finalprojectpam.ui.view.tiket.HomeTiketScreen
import com.example.finalprojectpam.ui.view.tiket.UpdateTiketScreen
import com.example.finalprojectpam.ui.view.transaksi.DestinasiDetailTransaksi
import com.example.finalprojectpam.ui.view.transaksi.DestinasiEntryTransaksi
import com.example.finalprojectpam.ui.view.transaksi.DestinasiHomeTransaksi
import com.example.finalprojectpam.ui.view.transaksi.DestinasiUpdateTransaksi
import com.example.finalprojectpam.ui.view.transaksi.DetailTransaksiScreen
import com.example.finalprojectpam.ui.view.transaksi.EntryTransaksiScreen
import com.example.finalprojectpam.ui.view.transaksi.HomeTransaksiScreen
import com.example.finalprojectpam.ui.view.transaksi.UpdateTransaksiScreen


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
               navigateToTiketScreen = { navController.navigate(DestinasiHomeTiket.route) },
               navigateToTransaksiScreen = { navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(DestinasiHomeEvent.route) {
            HomeEventScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryEvent.route) },
                ondetailClick = { id_event ->
                    navController.navigate("detail/$id_event")
                    Log.d("NavigationDebug", "Navigating to detail/$id_event")
                },
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(DestinasiEntryEvent.route) {
            EntryEventScreen(
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)},
                navigateBack = {
                    navController.navigate(DestinasiHomeEvent.route) {
                        popUpTo(DestinasiHomeEvent.route) {
                            inclusive = true
                        }
                    }
                }
            )
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
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
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
                    onBackClick = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeEvent.route) {
                            popUpTo(DestinasiHomeEvent.route) { inclusive = true }
                        }
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }
        composable(DestinasiHomePeserta.route) {
            HomePesertaScreen(
                navigateToItemEntryPeserta = { navController.navigate(DestinasiEntryPeserta.route) },
                ondetailClick = { id_peserta ->
                    navController.navigate("detail_peserta/$id_peserta")
                    Log.d("NavigationDebug", "Navigating to detail/$id_peserta")

                },
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(DestinasiEntryPeserta.route) {
            EntryPesertaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomePeserta.route) {
                        popUpTo(DestinasiHomePeserta.route) {
                            inclusive = true
                        }
                    }
                },
                onEventClick = {navController.navigate(DestinasiHomePeserta.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(
            route = "detail_peserta/{id_peserta}",
            arguments = listOf(
                navArgument("id_peserta") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_peserta = backStackEntry.arguments?.getInt(DestinasiDetailPeserta.Id_peserta)
            id_peserta?.let {
                DetailPesertaScreen(
                    idPeserta = it,
                    onEditClick = {
                        navController.navigate("${DestinasiUpdatePeserta.route}/$id_peserta")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }
        composable(
            route = "${DestinasiUpdatePeserta.route}/{id_peserta}",
            arguments = listOf(
                navArgument("id_peserta") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_peserta = backStackEntry.arguments?.getInt("id_peserta")
            id_peserta?.let {
                UpdatePesertaScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomePeserta.route) {
                            popUpTo(DestinasiHomePeserta.route) { inclusive = true }
                        }
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }
        composable(DestinasiHomeTiket.route) {
            HomeTiketScreen(
                navigateToItemEntryTiket = { navController.navigate(DestinasiEntryTiket.route) },
                ondetailClick = { id_tiket ->
                    navController.navigate("detail_tiket/$id_tiket")
                    Log.d("NavigationDebug", "Navigating to detail/$id_tiket")
                },
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(DestinasiEntryTiket.route) {
            EntryTiketScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTiket.route) {
                        popUpTo(DestinasiHomeTiket.route) {
                            inclusive = true
                        }
                    }
                },
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(
            route = "detail_tiket/{id_tiket}",
            arguments = listOf(
                navArgument("id_tiket") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_tiket = backStackEntry.arguments?.getInt(DestinasiDetailTiket.Id_tiket)
            id_tiket?.let {
                DetailTiketScreen(
                    idTiket = it,
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateTiket.route}/$id_tiket")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }
        composable(DestinasiUpdateTiket.routesWithArg,
            arguments = listOf(
                navArgument("id_tiket") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_tiket = backStackEntry.arguments?.getInt(DestinasiUpdateTiket.IdTiket)
            id_tiket?.let {
                UpdateTiketScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeTiket.route) {
                            popUpTo(DestinasiHomeTiket.route) { inclusive = true }
                        }
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }

        composable(DestinasiHomeTransaksi.route) {
            HomeTransaksiScreen(
                navigateToItemEntryTransaksi = { navController.navigate(DestinasiEntryTransaksi.route) },
                ondetailClick = { id_transaksi ->
                    navController.navigate("detail_transaksi/$id_transaksi")
                    Log.d("NavigationDebug", "Navigating to detail/$id_transaksi")

                },
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(DestinasiEntryTransaksi.route) {
            EntryTransaksiScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTransaksi.route) {
                        popUpTo(DestinasiHomeTransaksi.route) {
                            inclusive = true
                        }
                    }
                },
                onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
            )
        }
        composable(
            route = "detail_transaksi/{id_transaksi}",
            arguments = listOf(
                navArgument("id_transaksi") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_transaksi = backStackEntry.arguments?.getInt(DestinasiDetailTransaksi.Id_transaksi)
            id_transaksi?.let {
                DetailTransaksiScreen(
                    idTransaksi = it,
                    onEditClick = {
                        navController.navigate("update_transaksi/$id_transaksi")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }
        composable(
            route = "update_transaksi/{id_transaksi}",
            arguments = listOf(
                navArgument("id_transaksi") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_transaksi = backStackEntry.arguments?.getInt("id_transaksi")
            id_transaksi?.let {
                UpdateTransaksiScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeTransaksi.route) {
                            popUpTo(DestinasiHomeTransaksi.route) { inclusive = true }
                        }
                    },
                    onEventClick = {navController.navigate(DestinasiHomeEvent.route)},
                    onPesertaClick = {navController.navigate(DestinasiHomePeserta.route)},
                    onTiketClick = {navController.navigate(DestinasiHomeTiket.route)},
                    onTransaksiClick = {navController.navigate(DestinasiHomeTransaksi.route)}
                )
            }
        }
    }
}
