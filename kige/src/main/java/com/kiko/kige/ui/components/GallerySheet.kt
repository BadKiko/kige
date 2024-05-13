/*
 * Copyright (c) 2023. Created by Kiko
 */

package com.kiko.kige.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kiko.kige.data.remembers.rememberKigeState
import com.kiko.kige.data.state.GalleryState
import com.kiko.kige.data.state.KigeState
import com.kiko.kige.data.utils.GalleryUtils
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.Shimmer
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import com.skydoves.landscapist.rememberDrawablePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GallerySheet(
    rememberGalleryState: GalleryState,
    rememberKigeState: KigeState,
    onSelect: (painter: Painter, uri: Uri) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var selectedPhotoUri by remember { mutableStateOf("") }


    val selectedPainter = rememberAsyncImagePainter(
        model = selectedPhotoUri
    )

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
                                    coroutineScope.launch {
                                        selectedPhotoUri = photoUri
                                        val uri = Uri.fromFile(File(photoUri))

                                        onSelect(
                                            selectedPainter,
                                            uri
                                        )

                                        rememberGalleryState.hide(coroutineScope)
                                        delay(200) // Костыль надо придумать как возвращать hide при получении изображения
                                        rememberKigeState.hide(coroutineScope)
                                    }
                                },
                            component = rememberImageComponent {
                                +ShimmerPlugin(
                                    Shimmer.Resonate(
                                        baseColor = MaterialTheme.colorScheme.secondaryContainer,
                                        highlightColor = MaterialTheme.colorScheme.secondary
                                    ),
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