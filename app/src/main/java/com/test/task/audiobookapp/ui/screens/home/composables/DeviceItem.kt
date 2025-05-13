package com.test.task.audiobookapp.ui.screens.home.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.Device
import com.test.task.audiobookapp.data.model.DeviceStatus
import com.test.task.audiobookapp.ui.theme.AppPrimaryTextColor
import com.test.task.audiobookapp.ui.theme.ComponentsColor
import com.test.task.audiobookapp.ui.theme.LostOrangeColor
import com.test.task.audiobookapp.ui.theme.PersonCircleIconOrange

@Composable
fun DeviceItem(
    device: Device,
    isSelected: Boolean = false,
    isLastItem: Boolean = false,
    isNavigateNeeded: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = ComponentsColor,
        onClick = onClick
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_person_circle),
                    contentDescription = "Device image",
                    tint = if (device.status == DeviceStatus.LOST) PersonCircleIconOrange else AppPrimaryTextColor,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = device.label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (device.status == DeviceStatus.LOST) LostOrangeColor else AppPrimaryTextColor,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp).weight(1f)
                )

                if (isSelected) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_checked),
                        contentDescription = "Selected",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    if (isNavigateNeeded){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = "Navigate",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            if (!isLastItem) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp)
                )
            }
        }
    }
}