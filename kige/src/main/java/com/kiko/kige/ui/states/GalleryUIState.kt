/*
 * Copyright (c) 2023. Created by Kiko
 */

package com.kiko.kige.ui.states

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GalleryUIState(
    val title: String = "Choose image",
    val fontSize: TextUnit = 18.sp,
    val fontWeight: FontWeight = FontWeight.SemiBold,
    val imagesModifier: Modifier = Modifier
        .size(120.dp)
        .clip(RoundedCornerShape(16.dp))
)
