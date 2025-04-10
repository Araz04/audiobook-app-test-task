package com.test.task.audiobookapp.ui.composablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.ui.screens.home.HomeViewModel
import com.test.task.audiobookapp.ui.theme.AppPrimaryTextColor
import com.test.task.audiobookapp.ui.theme.ResetRedColor
import com.test.task.audiobookapp.ui.theme.SelectedItemsContainerColor
import org.koin.androidx.compose.koinViewModel

@Composable
fun SelectedItemsView(
    viewModel: HomeViewModel = koinViewModel(),
    onLostClicked: () -> Unit,
    onResetClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedCount by viewModel.selectedDevicesCount.collectAsStateWithLifecycle()

    Column {
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SelectedItemsContainerColor)
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (selectedCount > 0) "$selectedCount Items selected" else "Selected Items",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_info),
                        contentDescription = "More options",
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(0.dp)
                        .background(
                            color = SelectedItemsContainerColor,
                            shape = RoundedCornerShape(8.dp)
                        ).width(IntrinsicSize.Min) ,
                    containerColor = SelectedItemsContainerColor
                ) {

                    DropdownMenuItem(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.height(36.dp),
                        text = {
                            Text(
                                "Lost",
                                style = MaterialTheme.typography.bodyLarge,
                                color = AppPrimaryTextColor,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(align = Alignment.CenterVertically).padding(bottom = 8.dp)
                            )
                        },
                        onClick = {
                            expanded = false
                            onLostClicked()
                        }
                    )

                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

                    DropdownMenuItem(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.height(36.dp),
                        text = {
                            Text(
                                "Reset",
                                style = MaterialTheme.typography.bodyLarge,
                                color = ResetRedColor,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .wrapContentHeight(align = Alignment.CenterVertically).padding(top = 8.dp)
                            )
                        },
                        onClick = {
                            expanded = false
                            onResetClicked()
                        }
                    )
                }
            }
        }
    }
}