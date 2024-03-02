package com.example.nflstats.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp


/**
 * Creates a floating action button with a nice border, and padding [padding]
 */
@Composable
fun ActionButton(onClick: () -> Unit, padding: Double = 2.5, isSelectionMenu: Boolean = false, content: @Composable () -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val width = if(!isSelectionMenu) {
        when(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            true -> screenWidth * .95
            false -> screenWidth * .25
        }
    } else screenWidth * .95
    FloatingActionButton(onClick = onClick, modifier = Modifier.width(width.dp).padding(padding.dp), content = content)
}