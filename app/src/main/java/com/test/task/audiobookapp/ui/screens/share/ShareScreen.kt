package com.test.task.audiobookapp.ui.screens.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.test.task.audiobookapp.data.model.ShareOption
import com.test.task.audiobookapp.ui.screens.selectdevices.composables.ShareOptionItem
import com.test.task.audiobookapp.ui.theme.ComponentsColor
import com.test.task.audiobookapp.ui.theme.SurfaceColor

@Composable
fun ShareScreen(options: List<ShareOption>,
                onItemClick: (ShareOption) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().background(color = SurfaceColor)) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
        ) {
            LazyColumn(modifier = Modifier.background(color = ComponentsColor)) {
                itemsIndexed(
                    items = options,
                    key = { _, option -> option.id }
                ) { index, option ->

                    ShareOptionItem(shareOption = option, onClick = onItemClick, isLastItem = index == options.size - 1,)
                }
            }
        }
    }
}