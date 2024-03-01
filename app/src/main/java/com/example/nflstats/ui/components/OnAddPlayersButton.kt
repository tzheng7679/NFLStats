package com.example.nflstats.ui.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.model.Entity

@Composable
fun OnAddEntityButton(entity: Entity, onAddEntity: (Entity) -> Unit) {
    Spacer(Modifier.height(5.dp))
    Button(
        onClick = { onAddEntity(entity) },
        modifier = Modifier.width(100.dp)
    ) {
        Text(
            text = "Add This",
            fontSize = 3.em,
            textAlign = TextAlign.Center,
            color = Color(255,255,255)
        )
    }
}