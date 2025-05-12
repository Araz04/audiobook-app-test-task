package com.test.task.audiobookapp.data.model

import androidx.annotation.DrawableRes

data class ShareOption(
    val id: Int,
    val title: String,
    @DrawableRes val iconResId: Int
)