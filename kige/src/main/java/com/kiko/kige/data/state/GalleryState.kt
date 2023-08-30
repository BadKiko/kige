/*
 * Copyright (c) 2023. Created by Kiko
 */

package com.kiko.kige.data.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import com.kiko.kige.ui.states.GalleryUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class GalleryState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val sheetState: SheetState,
    val galleryUIState: GalleryUIState = GalleryUIState(),
    val visibleState: MutableState<Boolean>,
) {
    fun expand() {
        this.visibleState.value = true
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun hide(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            this.visibleState.value = false
        }
    }
}