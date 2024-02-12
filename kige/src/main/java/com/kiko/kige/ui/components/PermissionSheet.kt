package com.kiko.kige.ui.components

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kiko.kige.data.state.KigeState
import com.kiko.kige.data.state.PermissionState
import com.kiko.kige.data.utils.GalleryUtils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
internal fun PermissionSheet(
    onHide: () -> Unit,
    rememberKigePermissionState: PermissionState,
    onGivenPermission: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
// Camera permission state
    val readExternalPermission = if (Build.VERSION.SDK_INT < 33)
        rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    else
        rememberPermissionState(android.Manifest.permission.READ_MEDIA_IMAGES)

    val isGranted = remember { mutableStateOf(false) }

    if (isGranted.value) {
        onGivenPermission()
    }

    if (rememberKigePermissionState.visibleState.value) {
        ModalBottomSheet(
            sheetState = rememberKigePermissionState.sheetState,
            onDismissRequest = {
                onHide()
                rememberKigePermissionState.hide(coroutineScope)
            }) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GalleryUtils.fetchGalleryImages(LocalContext.current)
                Text(
                    rememberKigePermissionState.permissionUIState.title,
                    fontWeight = rememberKigePermissionState.permissionUIState.fontWeight,
                    fontSize = rememberKigePermissionState.permissionUIState.fontSize,
                    textAlign = rememberKigePermissionState.permissionUIState.textAlign
                )
                Text(rememberKigePermissionState.permissionUIState.contentText)
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    coroutineScope.launch { readExternalPermission.launchPermissionRequest() }
                        .invokeOnCompletion {
                            if (readExternalPermission.status.isGranted) isGranted.value = true
                        }
                }) {
                    Text(rememberKigePermissionState.permissionUIState.buttonText)
                }
                Spacer(Modifier.padding(16.dp))
            }
        }
    }
}