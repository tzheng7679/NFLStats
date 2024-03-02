package com.example.nflstats.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * Creates a floating action button with a nice border, and padding [padding]
 */
@Composable
fun ActionButton(onClick: () -> Unit, padding: Double = 2.5, content: @Composable () -> Unit) {
    FloatingActionButton(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(padding.dp), content = content)
}