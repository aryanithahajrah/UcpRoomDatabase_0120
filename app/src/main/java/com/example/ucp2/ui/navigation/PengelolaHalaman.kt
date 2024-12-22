package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.Dosen.DestinasiInsertDosen
import com.example.ucp2.ui.view.Dosen.DetailDosenView
import com.example.ucp2.ui.view.Dosen.HomeDosenView
import com.example.ucp2.ui.view.Dosen.HomeMenuView
import com.example.ucp2.ui.view.Dosen.InsertDosenView
import com.example.ucp2.ui.view.Matakuliah.DestinasiInsertMatakuliah
import com.example.ucp2.ui.view.Matakuliah.DetailMatakuliahView
import com.example.ucp2.ui.view.Matakuliah.HomeMatakuliahView
import com.example.ucp2.ui.view.Matakuliah.InsertMatakuliahView
import com.example.ucp2.ui.view.Matakuliah.UpdateMatkuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeMenu.route
    ) {
        composable(
            route = DestinasiHomeMenu.route
        ) {
            HomeMenuView(
                onDosenClick = {
                    navController.navigate(DestinasiHomeDosen.route)
                },
                onMatkulClick = {
                    navController.navigate(DestinasiHomeMatakuliah.route)
                }
            )
        }

        composable(
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDetailDosen.route}/$nidn")
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiHomeMatakuliah.route
        ) {
            HomeMatakuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMatakuliah.route}/$kode")
                },
                onAddMatkul = {
                    navController.navigate(DestinasiInsertMatakuliah.route)
                },
                modifier = modifier
            )
        }

        composable (
            route = DestinasiInsertDosen.route
        ) {
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable (
            DestinasiDetailDosen.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailDosen.NIDN) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailDosen.NIDN)

            kode?.let { kode ->
                DetailDosenView(
                    onBack = {
                        navController.popBackStack()
                    },
                )
            }
        }

        composable (
            DestinasiDetailMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatakuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailMatakuliah.KODE)

            kode?.let { kode ->
                DetailMatakuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMatakuliah.route}/$kode")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable (
            route = DestinasiInsertMatakuliah.route
        ) {
            InsertMatakuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable (
            DestinasiUpdateMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatakuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailMatakuliah.KODE)

            kode?.let { kode ->
                DetailMatakuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateMatakuliah.route}/$kode")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateMatakuliah.routesWithArg,
            arguments = listOf(
                navArgument (DestinasiUpdateMatakuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMatkuliahView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }
    }
}