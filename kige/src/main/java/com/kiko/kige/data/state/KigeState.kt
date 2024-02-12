/*
 * Copyright (c) 2023. Created by Kiko
 */

package com.kiko.kige.data.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.kiko.kige.ui.states.GalleryUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class KigeState(
    val visibleState: MutableState<Boolean>,
    val rememberGalleryState: GalleryState,
    val rememberPermissionState: PermissionState,
    private val _photoUri: MutableState<String> = mutableStateOf(""),
    val photoUri: State<String> = _photoUri,
) {
    fun expand() {
        this.visibleState.value = true
    }


    fun hide(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            rememberGalleryState.hide(coroutineScope)
        }.invokeOnCompletion {
            this.visibleState.value = false
        }
    }

    fun hide(coroutineScope: CoroutineScope, onHided: () -> Unit) {
        rememberGalleryState.hide(coroutineScope, onHided)
    }
}