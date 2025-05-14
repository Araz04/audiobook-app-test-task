package com.test.task.audiobookapp.ui.screens.share

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.ShareOption
import com.test.task.audiobookapp.ui.screens.selectdevices.SelectDevicesScreen
import com.test.task.audiobookapp.ui.screens.selectdevices.composables.ShareOptionView
import com.test.task.audiobookapp.ui.screens.share.bottomsheet.ShareBottomSheetContent
import com.test.task.audiobookapp.ui.screens.share.bottomsheet.ShareStep
import com.test.task.audiobookapp.ui.screens.share.bottomsheet.ShareTab
import com.test.task.audiobookapp.ui.stateholders.MainViewModel
import com.test.task.audiobookapp.ui.theme.SurfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareScreen(
    mainViewModel: MainViewModel,
    onPickImage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedImages by mainViewModel.selectedImages.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true, // Optional: fully expands by default
        confirmValueChange = { true }
    )
    val optionPhotos = ShareOption(
        id = 1, title = stringResource(R.string.photos), iconResId = R.drawable.ic_photos
    )
    val optionFiles = ShareOption(
        id = 2, title = stringResource(R.string.files), iconResId = R.drawable.ic_files
    )
    val optionNotes = ShareOption(
        id = 3, title = stringResource(R.string.notes), iconResId = R.drawable.ic_notes
    )
    val optionVoiceMemos = ShareOption(
        id = 4, title = stringResource(R.string.voice_memos), iconResId = R.drawable.ic_voice_memos
    )

    println(selectedImages)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = SurfaceColor)
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
        ) {
            ShareOptionView(
                shareOption = optionPhotos,
                onClick = { selectedOption ->
//                    showBottomSheet = true
                    onPickImage()
                },
            )
            ShareOptionView(
                shareOption = optionFiles,
                onClick = { selectedOption ->
                    showBottomSheet = true
                },
            )
            ShareOptionView(
                shareOption = optionNotes,
                onClick = { selectedOption ->
                    showBottomSheet = true
                },
            )
            ShareOptionView(
                shareOption = optionVoiceMemos,
                onClick = { selectedOption ->
                    showBottomSheet = false
                },
            )
        }
        if (selectedImages.isNotEmpty()){
            showBottomSheet = true
        }

        if (showBottomSheet){
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
//                ShareBottomSheetContent(
//                    initialTab = ShareTab.Photos,
//                    onDismiss = { showBottomSheet = false }
//                )
                SelectDevicesScreen(
                    fileUris = selectedImages,
                    onDismissBottomSheet = { showBottomSheet = false
                    mainViewModel.updateSelectedImages(emptySet())}
                )
            }
        }
    }
}

@Composable
fun ImageGrid(images: List<Uri>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(images) { uri ->
            AsyncImage(
                model = uri,
                contentDescription = "Selected image",
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}