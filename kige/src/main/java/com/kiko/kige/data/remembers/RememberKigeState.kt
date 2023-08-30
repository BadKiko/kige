package com.kiko.kige.data.remembers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.KigeState
import com.kiko.kige.data.state.PermissionState
import com.kiko.kige.ui.states.GalleryUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberKigeState(
    isVisibleOnInit: Boolean = false,
    rememberGalleryState: GalleryState = rememberKigeGalleryState(),
    rememberPermissionState: PermissionState = rememberKigePermissionState()
): KigeState {
    val visibleState = remember { mutableStateOf(isVisibleOnInit) }
    return remember {
        KigeState(visibleState, rememberGalleryState, rememberPermissionState)
    }
}