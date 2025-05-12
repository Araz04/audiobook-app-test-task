package com.test.task.audiobookapp.ui.screens.selectdevices

import android.net.Uri
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.ui.screens.home.composables.DeviceItem
import com.test.task.audiobookapp.ui.screens.home.composables.SearchField
import com.test.task.audiobookapp.ui.stateholders.SelectDevicesViewModel
import com.test.task.audiobookapp.ui.theme.AppBlueColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectDevicesScreen(viewModel: SelectDevicesViewModel, fileUris: List<Uri>) {
    val devices by viewModel.devices.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedDevices by viewModel.selectedDevices.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

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
                        )
                    }
                }
            }
        }
        Button(
            onClick = {

            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppBlueColor,
                contentColor = Color.White
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
}