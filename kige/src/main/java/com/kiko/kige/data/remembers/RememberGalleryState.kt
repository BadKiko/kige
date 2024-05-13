package com.kiko.kige.data.remembers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.PermissionState
import com.kiko.kige.ui.states.GalleryUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberKigeGalleryState(): GalleryState {
    val sheetState = rememberModalBottomSheetState()
    val visibleState = remember { mutableStateOf(false) }
    val galleryUIState = GalleryUIState.standardGalleryUiState()

    return remember {
        GalleryState(sheetState, galleryUIState, visibleState)
    }
}