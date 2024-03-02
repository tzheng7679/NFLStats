package com.example.nflstats.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

@Composable
fun AutosizedText(
    text: String,
    baseSize: TextUnit,
    color: Color = Color.Unspecified,
    textAlign: TextAlign = TextAlign.Left,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    fontFamily: FontFamily = FontFamily.Default
) {
    val textSize = remember { mutableStateOf(baseSize) }

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        textAlign = textAlign,
        fontSize = textSize.value, //size of font for team names
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        maxLines = 1,
        color = color,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = {
            if(it.isLineEllipsized(0)) textSize.value *= 0.9f
        },
        fontFamily = fontFamily
    )
}