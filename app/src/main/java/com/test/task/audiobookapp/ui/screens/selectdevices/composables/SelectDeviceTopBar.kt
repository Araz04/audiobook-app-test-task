package com.test.task.audiobookapp.ui.screens.selectdevices.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.DeviceType
import com.test.task.audiobookapp.ui.stateholders.SelectDevicesViewModel
import com.test.task.audiobookapp.ui.theme.AppContainersColor
import com.test.task.audiobookapp.ui.theme.AppPrimaryTextColor
import com.test.task.audiobookapp.ui.theme.ComponentsColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareTopBar(
    onBackClicked: () -> Unit,
    onSelectAll: () -> Unit,
    onCancel: () -> Unit,
    viewModel: SelectDevicesViewModel,
) {
    var showMenu by remember { mutableStateOf(false) }
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_blue),
                    contentDescription = "Add",
                    tint = Color.Unspecified
                )
            }
        },
        title = {
            TabRow(
                selectedTabIndex = if (selectedTab == DeviceType.IPHONE) 0 else 1,
                containerColor = AppContainersColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 0.dp)
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
        },
        actions = {
            IconButton(onClick = { showMenu = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more_info),
                    contentDescription = "More",
                    tint = Color.Unspecified
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false },
                modifier = Modifier
                    .width(220.dp)
                    .padding(0.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                DropDownShareMenuItem(
                    modifier = Modifier.padding(bottom = 6.dp),
                    title = stringResource(R.string.select_all),
                    onClick = {
                        showMenu = false
                        viewModel.selectAllDevices()
                    },
                    4.dp
                )

                HorizontalDivider()

                DropDownShareMenuItem(
                    modifier = Modifier.padding(top = 6.dp),
                    title = stringResource(R.string.cancel),
                    onClick = {
                        showMenu = false
                        viewModel.resetSelectedDevices()
                    },
                    4.dp
                )
            }
        }
    )
}

@Composable
fun DropDownShareMenuItem(modifier: Modifier = Modifier, title: String, onClick: () -> Unit, paddingVertical: Dp) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp).fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = AppPrimaryTextColor,
            modifier = modifier
        )
    }
}