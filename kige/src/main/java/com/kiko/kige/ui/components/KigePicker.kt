package com.kiko.kige.ui.components

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kiko.kige.data.remembers.rememberKigeGalleryState
import com.kiko.kige.data.remembers.rememberKigePermissionState
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.PermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun KigePicker(
    rememberKigeGalleryState: GalleryState = rememberKigeGalleryState(),
    rememberKigePermissionState: PermissionState = rememberKigePermissionState(),
    onPick: (String) -> Unit
) {
    val readExternalPermission = if (Build.VERSION.SDK_INT < 33)
        rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    else
        rememberPermissionState(Manifest.permission.READ_MEDIA_IMAGES)

    val coroutineScope = rememberCoroutineScope()

    if (readExternalPermission.status.isGranted) {
        CreateGallerySheet(onPick, coroutineScope, rememberKigeGalleryState)
    } else {
        PermissionSheet(rememberKigePermissionState) {
            CreateGallerySheet(onPick, coroutineScope, rememberKigeGalleryState)
        }

        LaunchedEffect(true) {
            coroutineScope.launch {
                rememberKigePermissionState.expand()
            }
        }
    }
}

@Composable
private fun CreateGallerySheet(
    onPick: (String) -> Unit,
    coroutineScope: CoroutineScope,
    rememberKigeGalleryState: GalleryState
) {
    GallerySheet(rememberKigeGalleryState, onPick)

    LaunchedEffect(true) {
        coroutineScope.launch {
            rememberKigeGalleryState.expand()
        }
    }
}