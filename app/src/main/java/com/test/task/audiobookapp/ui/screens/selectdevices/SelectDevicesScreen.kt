package com.test.task.audiobookapp.ui.screens.selectdevices

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.ui.screens.dialogs.SimpleMessageDialog
import com.test.task.audiobookapp.ui.screens.home.composables.DeviceItem
import com.test.task.audiobookapp.ui.screens.home.composables.SearchField
import com.test.task.audiobookapp.ui.screens.selectdevices.composables.ShareTopBar
import com.test.task.audiobookapp.ui.stateholders.SelectDevicesViewModel
import com.test.task.audiobookapp.ui.theme.AppBlueColor
import com.test.task.audiobookapp.ui.theme.AppButtonTextColor
import com.test.task.audiobookapp.ui.theme.DisabledButtonBackgroundColor
import com.test.task.audiobookapp.ui.theme.DisabledButtonTextColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectDevicesScreen(
    viewModel: SelectDevicesViewModel = koinViewModel(),
    fileUris: List<Uri>,
    onDismissBottomSheet: () -> Unit,
) {
    val devices by viewModel.devices.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedDevices by viewModel.selectedDevices.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    fun onShowDialog() {
        showDialog = true
    }

    Column(
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            ShareTopBar(
                viewModel = viewModel,
                onSelectAll = { viewModel.selectAllDevices() },
                onCancel = { viewModel.resetSelectedDevices() },
                onBackClicked = onDismissBottomSheet
            )

            SearchField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Card(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 0.dp
                ),
            ) {
                LazyColumn {
                    itemsIndexed(
                        items = devices,
                        key = { _, device -> device.id }
                    ) { index, device ->
                        DeviceItem(
                            device = device,
                            isSelected = selectedDevices.contains(device.id),
                            isLastItem = index == devices.size - 1,
                            onClick = {
                                viewModel.toggleDeviceSelection(device.id)
                            },
                            isNavigateNeeded = false
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                onShowDialog()
            },
            shape = RoundedCornerShape(12.dp),
            enabled = selectedDevices.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppBlueColor,
                contentColor = AppButtonTextColor,
                disabledContainerColor = DisabledButtonBackgroundColor, // Optional: style when disabled
                disabledContentColor = DisabledButtonTextColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.share_text),
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp,
            )
        }
    }

    if (showDialog) {
        SimpleMessageDialog(
            title = stringResource(R.string.success),
            message = stringResource(R.string.file_share_success_message),
            confirmButtonText = stringResource(R.string.text_ok),
            onDismiss = { showDialog = false
                onDismissBottomSheet()
            }
        )
    }
}