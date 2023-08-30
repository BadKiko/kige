package com.kiko.kige.data.remembers

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.kiko.kige.data.state.PermissionState
import com.kiko.kige.ui.states.PermissionUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberKigePermissionState(): PermissionState {
    val sheetState = rememberModalBottomSheetState()
    val visibleState = remember { mutableStateOf(false) }
    return remember {
        PermissionState(sheetState, visibleState, PermissionUIState())
    }
}