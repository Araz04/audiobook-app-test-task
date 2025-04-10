package com.test.task.audiobookapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.test.task.audiobookapp.R

sealed class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    object Home : BottomNavItem(R.string.home, R.drawable.ic_home, "home")
    object Share : BottomNavItem(R.string.share, R.drawable.ic_share, "share")
    object History : BottomNavItem(R.string.history, R.drawable.ic_history, "history")
    object Account : BottomNavItem(R.string.account, R.drawable.ic_account, "account")
}