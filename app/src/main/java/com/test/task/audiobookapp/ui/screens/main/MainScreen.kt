package com.test.task.audiobookapp.ui.screens.main

import android.util.Log
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
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.ShareOption
import com.test.task.audiobookapp.ui.navigation.BottomNavigationBar
import com.test.task.audiobookapp.ui.navigation.BottomNavItem
import com.test.task.audiobookapp.ui.screens.account.AccountScreen
import com.test.task.audiobookapp.ui.screens.history.HistoryScreen
import com.test.task.audiobookapp.ui.screens.home.HomeScreen
import com.test.task.audiobookapp.ui.screens.main.composables.AppTopBar
import com.test.task.audiobookapp.ui.screens.main.composables.SelectedItemsView
import com.test.task.audiobookapp.ui.stateholders.HomeViewModel
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

    val currentRoute by navController.currentBackStackEntryAsState()
    val currentDestination = currentRoute?.destination?.route



    Scaffold(
        topBar = {
            AppTopBar(
                selectionMode = selectionMode,
                onToggleSelectionMode = { viewModel.toggleSelectionMode() },
                onSelectAll = { viewModel.selectAllDevices() },
                onAdd = { },
                currentRoute = currentDestination
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
        val shareOptions = listOf(
            ShareOption(
                id = 1,
                title = "Photos",
                iconResId = R.drawable.ic_photos // Replace with your drawable
            ),
            ShareOption(
                id = 2,
                title = "Files",
                iconResId = R.drawable.ic_files
            ),
            ShareOption(
                id = 3,
                title = "Notes",
                iconResId = R.drawable.ic_notes
            ),
            ShareOption(
                id = 4,
                title = "Voice Memos",
                iconResId = R.drawable.ic_voice_memos
            )
        )
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(paddingValues)
        )
        {
            composable(BottomNavItem.Home.route) { HomeScreen(viewModel) }
            composable(BottomNavItem.Share.route) {
                ShareScreen(
                    options = shareOptions,
                    onItemClick = { selectedOption ->

                    }
                )
            }
            composable(BottomNavItem.History.route) { HistoryScreen() }
            composable(BottomNavItem.Account.route) { AccountScreen() }
        }
    }
}