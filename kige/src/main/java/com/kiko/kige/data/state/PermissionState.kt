package com.kiko.kige.data.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import com.kiko.kige.ui.states.PermissionUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class PermissionState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val sheetState: SheetState,
    val visibleState: MutableState<Boolean>,
    val permissionUIState: PermissionUIState
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