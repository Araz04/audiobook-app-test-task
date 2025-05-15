package com.test.task.audiobookapp.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.task.audiobookapp.ui.navigation.BottomNavigationBar
import com.test.task.audiobookapp.ui.navigation.BottomNavItem
import com.test.task.audiobookapp.ui.screens.account.AccountScreen
import com.test.task.audiobookapp.ui.screens.history.HistoryScreen
import com.test.task.audiobookapp.ui.screens.home.HomeScreen
import com.test.task.audiobookapp.ui.screens.main.composables.AppTopBar
import com.test.task.audiobookapp.ui.screens.main.composables.SelectedItemsView
import com.test.task.audiobookapp.ui.stateholders.HomeViewModel
import com.test.task.audiobookapp.ui.screens.share.ShareScreen
import com.test.task.audiobookapp.ui.stateholders.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    onPickImages: () -> Unit,
    onPickDocuments: () -> Unit,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Share,
        BottomNavItem.History,
        BottomNavItem.Account
    )

    val selectionMode by homeViewModel.selectionMode.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isSelectDeviceScreen = currentRoute?.startsWith("select_device_screen") == true

    Scaffold(
        topBar = {
                AppTopBar(
                    selectionMode = selectionMode,
                    onToggleSelectionMode = { homeViewModel.toggleSelectionMode() },
                    onSelectAll = { homeViewModel.selectAllDevices() },
                    onAdd = { },
                    currentRoute = currentRoute
                )
        },
        bottomBar = {
            if (selectionMode) {
                SelectedItemsView(
                    onLostClicked = {
                        homeViewModel.markSelectedAsLost()
                    },
                    onResetClicked = {
                        homeViewModel.resetSelectedDevices()
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
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        )
        {
            composable(BottomNavItem.Home.route) { HomeScreen(homeViewModel) }
            composable(BottomNavItem.Share.route) { ShareScreen(mainViewModel = mainViewModel, onPickImages = onPickImages, onPickDocuments = onPickDocuments, homeViewModel = homeViewModel) }
            composable(BottomNavItem.History.route) { HistoryScreen() }
            composable(BottomNavItem.Account.route) { AccountScreen() }
        }
    }
}