/*
 * Copyright (c) 2023. Created by Kiko
 */

package com.kiko.kige.ui.states

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiko.kige.R
import org.w3c.dom.Text

data class PermissionUIState(
    val title: String = "Need permission",
    val fontSize: TextUnit = 18.sp,
    val fontWeight: FontWeight = FontWeight.SemiBold,
    val textAlign: TextAlign = TextAlign.Center,
    val contentText: String = "App need request permission for gallery use",
    val buttonText: String = "Give"
) {
    companion object {
        @Composable
        fun standardPermissionUiState(): PermissionUIState {
            return PermissionUIState(
                title = stringResource(R.string.gallery_title),
                contentText = stringResource(R.string.gallery_content),
                buttonText = stringResource(R.string.gallery_button)
            )
        }
    }
}
