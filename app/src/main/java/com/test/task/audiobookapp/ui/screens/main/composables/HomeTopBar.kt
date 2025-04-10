package com.test.task.audiobookapp.ui.screens.main.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.test.task.audiobookapp.R
import com.test.task.audiobookapp.ui.navigation.BottomNavItem
import com.test.task.audiobookapp.ui.theme.AppBlueColor
import com.test.task.audiobookapp.ui.theme.AppContainersColor
import com.test.task.audiobookapp.ui.theme.AppPrimaryTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    selectionMode: Boolean,
    onToggleSelectionMode: () -> Unit,
    onSelectAll: () -> Unit,
    onAdd: () -> Unit,
    currentRoute: String?
) {
    var showMenu by remember { mutableStateOf(false) }
    val title = when (currentRoute) {
        BottomNavItem.Home.route -> stringResource(R.string.home)
        BottomNavItem.Share.route -> stringResource(R.string.share)
        BottomNavItem.History.route -> stringResource(R.string.history)
        BottomNavItem.Account.route -> stringResource(R.string.account)
        else -> stringResource(R.string.home) // Default to "Home" if route is unknown
    }

    TopAppBar(
        modifier = Modifier.padding(end = 16.dp),
        navigationIcon = {
            if (currentRoute == BottomNavItem.Home.route) {
                IconButton(onClick = onAdd) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Add",
                        tint = Color.Unspecified
                    )
                }
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (!selectionMode) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppPrimaryTextColor
                    )
                }
            }
        },
        actions = {
            if (selectionMode) {
                ButtonTopAppBar(stringResource(R.string.cancel), onToggleSelectionMode)
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_info),
                        contentDescription = "More",
                        tint = Color.Unspecified
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier
                        .width(220.dp)
                        .height(48.dp).padding(0.dp),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(R.string.select_all),
                                style = MaterialTheme.typography.bodyLarge,
                                color = AppPrimaryTextColor,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .wrapContentHeight(align = Alignment.CenterVertically)
                                    .padding(bottom = 16.dp)
                            )
                        },
                        onClick = {
                            showMenu = false
                            onSelectAll()
                        }
                    )
                }

            } else {
                if (currentRoute == BottomNavItem.Home.route) {
                    ButtonTopAppBar(stringResource(R.string.select), onToggleSelectionMode)
                }
            }
        }
    )
}

@Composable
fun ButtonTopAppBar(buttonText: String, onToggleSelectionMode: () -> Unit) {
    Button(
        onClick = onToggleSelectionMode,
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppContainersColor,
            contentColor = AppBlueColor
        ),
        modifier = Modifier
            .height(30.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


