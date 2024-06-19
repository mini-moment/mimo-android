package com.mimo.android.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun getRealPathFromURI(context: Context, contentUri: Uri): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(contentUri, projection, null, null, null)
    val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val filePath = cursor?.getString(columnIndex!!)
    cursor?.close()
    return filePath!!
}

fun convertBitmapToFile(context : Context,bitmap: Bitmap): File {
    val rootPath =
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            .toString()
    val file = File(
        "$rootPath/clip.jpeg",
    )
    try {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return file
}
