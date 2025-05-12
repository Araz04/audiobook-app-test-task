package com.test.task.audiobookapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.DeviceType
import com.test.task.audiobookapp.ui.screens.home.composables.SearchField
import com.test.task.audiobookapp.ui.screens.home.composables.SwipeableDeviceItem
import com.test.task.audiobookapp.ui.stateholders.HomeViewModel
import com.test.task.audiobookapp.ui.theme.AppContainersColor
import com.test.task.audiobookapp.ui.theme.AppPrimaryTextColor
import com.test.task.audiobookapp.ui.theme.ComponentsColor


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val devices by viewModel.devices.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectionMode by viewModel.selectionMode.collectAsStateWithLifecycle()
    val selectedDevices by viewModel.selectedDevices.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchField(
            value = searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        TabRow(
            selectedTabIndex = if (selectedTab == DeviceType.IPHONE) 0 else 1,
            containerColor = AppContainersColor,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .height(36.dp),
            contentColor = AppPrimaryTextColor,
            indicator = { },
            divider = { }
        ) {
            Tab(
                selected = selectedTab == DeviceType.IPHONE,
                onClick = { viewModel.setTab(DeviceType.IPHONE) },
                modifier = Modifier
                    .padding(2.dp)
                    .background(
                        color = if (selectedTab == DeviceType.IPHONE) ComponentsColor else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(32.dp),
                text = {
                    Text(
                        text = stringResource(R.string.iphone),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (selectedTab == DeviceType.IPHONE) FontWeight.Bold else FontWeight.Normal,
                        ),
                        color = AppPrimaryTextColor,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 4.dp
                        )

                    )
                }
            )

            Tab(
                selected = selectedTab == DeviceType.ANDROID,
                onClick = { viewModel.setTab(DeviceType.ANDROID) },
                modifier = Modifier
                    .padding(2.dp)
                    .background(
                        color = if (selectedTab == DeviceType.ANDROID) ComponentsColor else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(32.dp),
                text = {
                    Text(
                        text = stringResource(R.string.android),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (selectedTab == DeviceType.ANDROID) FontWeight.Bold else FontWeight.Normal,
                        ),
                        color = AppPrimaryTextColor,
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 2.dp
                        )
                    )
                }
            )
        }

        Card(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
        ) {
            LazyColumn {
                itemsIndexed(
                    items = devices,
                    key = { _, device -> device.id }
                ) { index, device ->
                    SwipeableDeviceItem(
                        device = device,
                        isSelected = selectedDevices.contains(device.id),
                        isLastItem = index == devices.size - 1,
                        onClick = {
                            if (selectionMode) {
                                viewModel.toggleDeviceSelection(device.id)
                            } else {
                                //Todo navigate to device details screen
                            }
                        },
                        onLostClick = { viewModel.markDeviceAsLost(device.id) },
                        onResetClick = { viewModel.resetDevice(device.id) },
                        onRecoverClick = { viewModel.recoverDevice(device.id) }
                    )
                }
            }
        }
    }
}





