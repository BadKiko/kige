package com.kiko.kige.ui.components

import android.Manifest
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kiko.kige.data.remembers.rememberKigeState
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.KigeState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun KigePicker(
    rememberKigeState: KigeState = rememberKigeState(), onSelect: (painter: Painter, uri: Uri) -> Unit
) {
    if (rememberKigeState.visibleState.value) {
        val readExternalPermission =
            if (Build.VERSION.SDK_INT < 33) rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
            else rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

        val coroutineScope = rememberCoroutineScope()

        if (readExternalPermission.status.isGranted) {
            CreateGallerySheet(
                rememberKigeState.rememberGalleryState, rememberKigeState, onSelect
            )
        } else {
            PermissionSheet(
                { rememberKigeState.hide(coroutineScope) },
                rememberKigeState.rememberPermissionState
            ) {
                CreateGallerySheet(
                    rememberKigeState.rememberGalleryState,
                    rememberKigeState,
                    onSelect
                )
            }

            SideEffect {
                rememberKigeState.rememberPermissionState.expand()
            }
        }
    }
}

@Composable
private fun CreateGallerySheet(
    rememberKigeGalleryState: GalleryState,
    rememberKigeState: KigeState,
    onSelect: (painter: Painter, uri: Uri) -> Unit
) {
    GallerySheet(rememberKigeGalleryState, rememberKigeState, onSelect)

    SideEffect {
        rememberKigeGalleryState.expand()
    }
}