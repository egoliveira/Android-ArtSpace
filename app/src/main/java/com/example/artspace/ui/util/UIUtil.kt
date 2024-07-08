package com.example.artspace.ui.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isPortrait(): Boolean {
    return LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
}