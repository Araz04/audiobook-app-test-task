package com.test.task.audiobookapp.ui.screens.home.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.domain.model.Device
import com.test.task.audiobookapp.domain.model.DeviceStatus
import com.test.task.audiobookapp.ui.theme.LostOrangeColor
import com.test.task.audiobookapp.ui.theme.ResetRedColor
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeableDeviceItem(
    device: Device,
    isSelected: Boolean = false,
    isLastItem: Boolean = false,
    onClick: () -> Unit,
    onLostClick: () -> Unit,
    onResetClick: () -> Unit,
    onRecoverClick: () -> Unit,
) {
    val swipeDistance = 172.dp
    val swipePx = with(LocalDensity.current) { swipeDistance.toPx() }
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(IntrinsicSize.Min)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        scope.launch {
                            if (swipeOffset.value < -swipePx / 2) {
                                swipeOffset.animateTo(-swipePx)
                            } else {
                                swipeOffset.animateTo(0f)
                            }
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        val newOffset = (swipeOffset.value + dragAmount).coerceIn(-swipePx, 0f)
                        scope.launch {
                            swipeOffset.snapTo(newOffset)
                        }
                        change.consume()
                    }
                )
            }
    ) {

        DeviceItem(
            device = device,
            isSelected = isSelected,
            isLastItem = isLastItem,
            onClick = {
                if (swipeOffset.value == 0f) {
                    onClick()
                } else {
                    scope.launch { swipeOffset.animateTo(0f) }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(swipeDistance)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .offset {
                        IntOffset((swipePx + swipeOffset.value).roundToInt(), 0)
                    },
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        if (device.status == DeviceStatus.LOST) {
                            onRecoverClick()
                        } else {
                            onLostClick()
                        }
                        scope.launch { swipeOffset.animateTo(0f) }
                    },
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .width(86.dp)
                        .background(LostOrangeColor),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        text = if (device.status == DeviceStatus.LOST) stringResource(R.string.recover) else stringResource(R.string.lost),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                TextButton(
                    onClick = {
                        onResetClick()
                        scope.launch { swipeOffset.animateTo(0f) }
                    },
                    modifier = Modifier
                        .width(86.dp)
                        .height(IntrinsicSize.Min)
                        .background(ResetRedColor),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Text(
                        "Reset",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}