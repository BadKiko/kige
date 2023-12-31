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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.kiko.kige.data.remembers.rememberKigeState
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.KigeState
import com.kiko.kige.data.utils.GalleryUtils
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GallerySheet(
    rememberGalleryState: GalleryState,
    rememberKigeState: KigeState,
) {
    val coroutineScope = rememberCoroutineScope()

    if (rememberGalleryState.visibleState.value) {
        ModalBottomSheet(
            sheetState = rememberGalleryState.sheetState,
            onDismissRequest = {
                rememberKigeState.hide(coroutineScope)
            }) {
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
                        CoilImage(
                            modifier = rememberGalleryState.galleryUIState.imagesModifier
                                .clickable {
                                    rememberKigeState.hide(coroutineScope) {
                                        rememberKigeState.choosePhoto(photoUri)
                                    }
                                },
                            component = rememberImageComponent {
                                // shows a shimmering effect when loading an image.
                                +ShimmerPlugin(
                                    baseColor = MaterialTheme.colorScheme.secondaryContainer,
                                    highlightColor = MaterialTheme.colorScheme.secondary
                                )
                            },
                            imageModel = { photoUri }, // loading a network image or local resource using an URL.
                            imageOptions = ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center
                            )
                        )
                    }
                }
            }
        }
    }
}