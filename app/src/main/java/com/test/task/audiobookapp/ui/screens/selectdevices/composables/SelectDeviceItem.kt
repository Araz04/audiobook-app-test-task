package com.test.task.audiobookapp.ui.screens.selectdevices.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.test.task.audiobookapp.data.model.ShareOption
import com.test.task.audiobookapp.ui.theme.ComponentsColor

@Composable
fun ShareOptionItem(
    shareOption: ShareOption,
    modifier: Modifier = Modifier,
    onClick: (ShareOption) -> Unit,
    isLastItem: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = ComponentsColor)
            .clickable {
                onClick(shareOption)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = shareOption.iconResId),
                contentDescription = "Device image",
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = shareOption.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp, horizontal = 12.dp)
                    .weight(1f)
            )
        }

        if (!isLastItem) {
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}