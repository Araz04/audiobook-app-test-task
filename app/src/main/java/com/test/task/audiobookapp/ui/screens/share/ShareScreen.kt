package com.test.task.audiobookapp.ui.screens.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.data.model.ShareOption
import com.test.task.audiobookapp.ui.screens.selectdevices.SelectDevicesScreen
import com.test.task.audiobookapp.ui.screens.selectdevices.composables.ShareOptionView
import com.test.task.audiobookapp.ui.stateholders.HomeViewModel
import com.test.task.audiobookapp.ui.stateholders.MainViewModel
import com.test.task.audiobookapp.ui.theme.SurfaceColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareScreen(
    homeViewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    onPickImages: () -> Unit,
    onPickDocuments: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectedImages by mainViewModel.selectedUris.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    val optionPhotos = ShareOption(
        id = 1, title = stringResource(R.string.photos), iconResId = R.drawable.ic_photos
    )
    val optionFiles = ShareOption(
        id = 2, title = stringResource(R.string.files), iconResId = R.drawable.ic_files
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
                    onPickImages()
                },
            )
            ShareOptionView(
                shareOption = optionFiles,
                onClick = { selectedOption ->
                    onPickDocuments()
                },
            )
        }

        if (selectedImages.isNotEmpty()) {
            showBottomSheet = true
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            ) {
                SelectDevicesScreen(
                    homeViewModel = homeViewModel,
                    fileUris = selectedImages,
                    onDismissBottomSheet = {
                        showBottomSheet = false
                        mainViewModel.updateSelectedUris(emptySet())
                        homeViewModel.resetSelectedDevices()
                    }
                )
            }
        }
    }
}