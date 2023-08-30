/*
 * Copyright (c) 2023. Created by Kiko
 */

package com.kiko.kige.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.utils.GalleryUtils

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun GallerySheet(rememberGalleryState: GalleryState, onPick: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    if (rememberGalleryState.visibleState.value) {
        ModalBottomSheet(
            sheetState = rememberGalleryState.sheetState,
            onDismissRequest = { rememberGalleryState.hide(coroutineScope) }) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GalleryUtils.fetchGalleryImages(LocalContext.current)
                Text(
                    rememberGalleryState.galleryUIState.title,
                    fontWeight = rememberGalleryState.galleryUIState.fontWeight,
                    fontSize = rememberGalleryState.galleryUIState.fontSize
                )
                val photos = GalleryUtils.fetchGalleryImages(LocalContext.current)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(photos) { photoUri ->
                        AsyncImage(
                            modifier = rememberGalleryState.galleryUIState.imagesModifier
                                .clickable {
                                    onPick(photoUri)
                                    rememberGalleryState.hide(coroutineScope)
                                },
                            model = photoUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}