package com.test.task.audiobookapp.ui.screens.main

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.ShareOption
import com.test.task.audiobookapp.ui.navigation.BottomNavigationBar
import com.test.task.audiobookapp.ui.navigation.BottomNavItem
import com.test.task.audiobookapp.ui.screens.account.AccountScreen
import com.test.task.audiobookapp.ui.screens.history.HistoryScreen
import com.test.task.audiobookapp.ui.screens.home.HomeScreen
import com.test.task.audiobookapp.ui.screens.main.composables.AppTopBar
import com.test.task.audiobookapp.ui.screens.main.composables.SelectedItemsView
import com.test.task.audiobookapp.ui.screens.selectdevices.SelectDevicesScreen
import com.test.task.audiobookapp.ui.stateholders.HomeViewModel
import com.test.task.audiobookapp.ui.screens.share.ShareScreen
import com.test.task.audiobookapp.ui.screens.selectdevices.composables.ShareTopBar
import com.test.task.audiobookapp.ui.stateholders.MainViewModel
import com.test.task.audiobookapp.ui.stateholders.SelectDevicesViewModel
import org.koin.androidx.compose.koinViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onPickImages: () -> Unit,
    onPickDocuments: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Share,
        BottomNavItem.History,
        BottomNavItem.Account
    )

    val selectionMode by viewModel.selectionMode.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isSelectDeviceScreen = currentRoute?.startsWith("select_device_screen") == true

    Scaffold(
        topBar = {
                AppTopBar(
                    selectionMode = selectionMode,
                    onToggleSelectionMode = { viewModel.toggleSelectionMode() },
                    onSelectAll = { viewModel.selectAllDevices() },
                    onAdd = { },
                    currentRoute = currentRoute
                )
        },
        bottomBar = {
            if (selectionMode) {
                SelectedItemsView(
                    onLostClicked = {
                        viewModel.markSelectedAsLost()
                    },
                    onResetClicked = {
                        viewModel.resetSelectedDevices()
                    }
                )
            } else {
                if (isSelectDeviceScreen) {

                } else {
                    BottomNavigationBar(navController = navController, items = items)

                }
            }
        }

    ) { paddingValues ->

        val uriList: List<Uri> = listOf()

        val encodedUriList = uriList.joinToString(separator = ",") {
            URLEncoder.encode(
                it.toString(),
                StandardCharsets.UTF_8.toString()
            )
        }

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        )
        {
            composable(BottomNavItem.Home.route) { HomeScreen(viewModel) }
            composable(BottomNavItem.Share.route) { ShareScreen(mainViewModel = mainViewModel, onPickImages = onPickImages, onPickDocuments = onPickDocuments) }
            composable(BottomNavItem.History.route) { HistoryScreen() }
            composable(BottomNavItem.Account.route) { AccountScreen() }
//            composable(
//                route = "select_device_screen/{uriList}",
//                arguments = listOf(navArgument("uriList") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val encodedList = backStackEntry.arguments?.getString("uriList") ?: ""
//                val uriList = encodedList.split(",")
//                    .mapNotNull {
//                        try {
//                            Uri.parse(URLDecoder.decode(it, StandardCharsets.UTF_8.toString()))
//                        } catch (e: Exception) {
//                            null
//                        }
//                    }
//
//                SelectDevicesScreen(viewModel = selectDevicesViewModel, fileUris = uriList)
//            }
        }
    }
}