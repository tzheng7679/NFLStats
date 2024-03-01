package com.example.nflstats.ui.components


import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.nflstats.model.Entity

@Composable
fun OnAddEntityButton(entity: Entity, onAddEntity: (Entity) -> Unit) {
    var adjustedFontSize by remember { mutableStateOf(2.0) }
    Spacer(Modifier.height(5.dp))
    Button(
        onClick = { onAddEntity(entity) },
        modifier = Modifier.width(100.dp).height(30.dp)
    ) {
        Text(
            text = "Store This",
            fontSize = adjustedFontSize.em,
            textAlign = TextAlign.Center,
            color = Color(255,255,255),
            onTextLayout = {it ->
                Log.d("HelpMe", "Starting...")
                if(it.didOverflowWidth || it.didOverflowWidth) {
                    Log.d("HelpMe", "Adjusting...")
                    adjustedFontSize *= .5
                }
            }
        )
    }
}