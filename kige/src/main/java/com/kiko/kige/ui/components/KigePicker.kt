package com.kiko.kige.ui.components

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kiko.kige.data.remembers.rememberKigeGalleryState
import com.kiko.kige.data.remembers.rememberKigePermissionState
import com.kiko.kige.data.remembers.rememberKigeState
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.KigeState
import com.kiko.kige.data.state.PermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun KigePicker(
    rememberKigeState: KigeState = rememberKigeState(),
    onSelect: (painter: Painter) -> Unit
) {
    if (rememberKigeState.visibleState.value) {
        val readExternalPermission = if (Build.VERSION.SDK_INT < 33)
            rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
        else
            rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

        val coroutineScope = rememberCoroutineScope()

        if (readExternalPermission.status.isGranted) {
            CreateGallerySheet(
                coroutineScope,
                rememberKigeState.rememberGalleryState,
                rememberKigeState,
                onSelect
            )
        } else {
            PermissionSheet(rememberKigeState.rememberPermissionState) {
                CreateGallerySheet(
                    coroutineScope,
                    rememberKigeState.rememberGalleryState,
                    rememberKigeState,
                    onSelect
                )
            }

            LaunchedEffect(true) {
                coroutineScope.launch {
                    rememberKigeState.rememberPermissionState.expand()
                }
            }
        }
    }
}

@Composable
private fun CreateGallerySheet(
    coroutineScope: CoroutineScope,
    rememberKigeGalleryState: GalleryState,
    rememberKigeState: KigeState,
    onSelect: (painter: Painter) -> Unit
) {
    GallerySheet(rememberKigeGalleryState, rememberKigeState, onSelect)

    LaunchedEffect(true) {
        coroutineScope.launch {
            rememberKigeGalleryState.expand()
        }
    }
}