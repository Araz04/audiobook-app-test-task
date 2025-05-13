package com.test.task.audiobookapp.ui.screens.share.bottomsheet

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.DeviceType
import com.test.task.audiobookapp.ui.screens.selectdevices.SelectDevicesScreen
import com.test.task.audiobookapp.ui.theme.AppBlueColor
import com.test.task.audiobookapp.ui.theme.AppContainersColor
import com.test.task.audiobookapp.ui.theme.AppPrimaryTextColor
import com.test.task.audiobookapp.ui.theme.ComponentsColor
import com.test.task.audiobookapp.ui.theme.ContinueButtonBackgroundColor
import com.test.task.audiobookapp.ui.theme.ContinueButtonTextColor
import com.test.task.audiobookapp.ui.theme.DisabledContinueBackgroundColor
import com.test.task.audiobookapp.ui.theme.DisabledContinueButtonTextColor

@Composable
fun ShareBottomSheetContent(
    initialTab: ShareTab = ShareTab.Photos, // or Albums
    onDismiss: () -> Unit,
) {
    var step by remember { mutableStateOf(ShareStep.SelectContent) }
    var selectedTab by remember { mutableStateOf(initialTab) }
    var selectedUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    when (step) {
        ShareStep.SelectContent -> {
            Box() {

                Column(modifier = Modifier.fillMaxHeight(0.9f), Arrangement.spacedBy(8.dp)) {
                    TabRow(selectedTab = selectedTab, onTabSelected = {
                        selectedTab = it
                    }
                    )

                    when (selectedTab) {
                        ShareTab.Photos -> PhotoGrid(
                            onSelectionChanged = { selectedUris = it }
                        )

                        ShareTab.Albums -> AlbumList(
                            onSelectionChanged = { selectedUris = it }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { step = ShareStep.SelectDevice },
                    enabled = selectedUris.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ContinueButtonBackgroundColor,
                        disabledContentColor = DisabledContinueButtonTextColor,
                        disabledContainerColor = DisabledContinueBackgroundColor,
                        contentColor = ContinueButtonTextColor
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.text_continue), modifier = Modifier.padding(8.dp),
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        ShareStep.SelectDevice -> {
            SelectDevicesScreen(
                fileUris = selectedUris,
                onDismissBottomSheet = onDismiss
            )
        }
    }
}

enum class ShareStep {
    SelectContent,
    SelectDevice
}

enum class ShareTab(val title: String) {
    Photos("Photos"),
    Albums("Albums")
}

@Composable
fun TabRow(selectedTab: ShareTab, onTabSelected: (ShareTab) -> Unit) {
    val tabs = ShareTab.entries.toTypedArray()

    TabRow(selectedTabIndex = tabs.indexOf(selectedTab),
        contentColor = AppPrimaryTextColor,
        containerColor = AppContainersColor,
        modifier = Modifier
            .padding(horizontal = 64.dp, vertical = 8.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp))
            .height(36.dp),
        indicator = { },
        divider = { }) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                modifier = Modifier
                    .padding(2.dp)
                    .background(
                        color = if (tab == selectedTab) ComponentsColor else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(32.dp),
                text = {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (tab == selectedTab) FontWeight.Bold else FontWeight.Normal,
                        ),
                        color = AppPrimaryTextColor,
                        modifier = Modifier.padding(
                            horizontal = 24.dp,
                            vertical = 2.dp
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun PhotoGrid(onSelectionChanged: (List<Uri>) -> Unit) {
    val photos = remember { generateDummyPhotos() }
    val selected = remember { mutableStateListOf<Uri>() }

    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier = Modifier.padding(bottom = 78.dp )) {
        items(photos) { uri ->
            PhotoItem(
                uri = uri,
                selected = selected.contains(uri),
                onClick = {
                    if (selected.contains(uri)) {
                        selected.remove(uri)
                    } else {
                        selected.add(uri)
                    }
                    onSelectionChanged(selected.toList())
                }
            )
        }
    }
}

@Composable
fun PhotoItem(uri: Uri, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clickable { onClick() }
            .border(
                width = if (selected) 3.dp else 1.dp,
                color = if (selected) AppBlueColor else Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        // Replace with real image loader
        Icon(Icons.Filled.Lock, contentDescription = null, modifier = Modifier.fillMaxSize())
    }
}

fun generateDummyPhotos(): List<Uri> {
    return List(30) { Uri.parse("content://dummy/photo_$it") }
}

@Composable
fun AlbumList(onSelectionChanged: (List<Uri>) -> Unit) {
    val albums = remember { generateDummyAlbums() }
    val selected = remember { mutableStateListOf<Uri>() }

    LazyColumn {
        items(albums) { album ->
            AlbumItem(
                album = album,
                selected = selected.contains(album.uri),
                onClick = {
                    if (selected.contains(album.uri)) {
                        selected.remove(album.uri)
                    } else {
                        selected.add(album.uri)
                    }
                    onSelectionChanged(selected.toList())
                }
            )
        }
    }
}

data class Album(val title: String, val uri: Uri)

@Composable
fun AlbumItem(album: Album, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            .background(
                if (selected) AppBlueColor.copy(alpha = 0.1f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.DateRange, contentDescription = null)
        Spacer(modifier = Modifier.width(12.dp))
        Text(album.title, style = MaterialTheme.typography.bodyLarge)
    }
}

fun generateDummyAlbums(): List<Album> {
    return listOf(
        Album("Vacation 2023", Uri.parse("content://album/1")),
        Album("Work Events", Uri.parse("content://album/2")),
        Album("Screenshots", Uri.parse("content://album/3")),
        Album("Camera Roll", Uri.parse("content://album/4"))
    )
}