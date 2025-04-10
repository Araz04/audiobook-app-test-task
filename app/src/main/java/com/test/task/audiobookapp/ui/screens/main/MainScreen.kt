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
import com.test.task.audiobookapp.ui.composablecomponents.SelectedItemsView
import com.test.task.audiobookapp.ui.navigation.BottomNavigationBar
import com.test.task.audiobookapp.ui.navigation.BottomNavItem
import com.test.task.audiobookapp.ui.screens.account.AccountScreen
import com.test.task.audiobookapp.ui.screens.history.HistoryScreen
import com.test.task.audiobookapp.ui.screens.home.HomeScreen
import com.test.task.audiobookapp.ui.screens.home.HomeViewModel
import com.test.task.audiobookapp.ui.screens.home.composables.AppTopBar
import com.test.task.audiobookapp.ui.screens.share.ShareScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: HomeViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Share,
        BottomNavItem.History,
        BottomNavItem.Account
    )

    val selectionMode by viewModel.selectionMode.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopBar(
                selectionMode = selectionMode,
                onToggleSelectionMode = { viewModel.toggleSelectionMode() },
                onSelectAll = { viewModel.selectAllDevices() },
                onAdd = {},
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
                BottomNavigationBar(navController = navController, items = items)
            }
        }

    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        )
        {
            composable(BottomNavItem.Home.route) { HomeScreen(viewModel) }
            composable(BottomNavItem.Share.route) { ShareScreen() }
            composable(BottomNavItem.History.route) { HistoryScreen() }
            composable(BottomNavItem.Account.route) { AccountScreen() }
        }
    }
}