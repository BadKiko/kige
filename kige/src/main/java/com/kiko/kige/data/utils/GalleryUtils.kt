package com.kiko.kige.data.utils

import android.app.Activity
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap

object GalleryUtils {
    fun fetchGalleryImages(context: Context): List<String> {
        val columns = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media._ID
        ) // get all columns of type images

        val orderBy = MediaStore.Images.Media.DATE_TAKEN // order data by date

        val imageCursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns,
            null,
            null,
            "$orderBy DESC"
        ) // get all data in Cursor by sorting in DESC order

        val galleryImageUrls = mutableListOf<String>()
        imageCursor?.use { cursor ->
            val dataColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) // get column index
            while (cursor.moveToNext()) {
                galleryImageUrls.add(cursor.getString(dataColumnIndex)) // get Image from column index
            }
        }

        return galleryImageUrls
    }

}