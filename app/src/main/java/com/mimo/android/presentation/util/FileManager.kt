package com.mimo.android.presentation.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun getRealPathFromURI(context: Context, contentUri: Uri): String {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = context.contentResolver.query(contentUri, projection, null, null, null)
    val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val filePath = cursor?.getString(columnIndex!!)
    cursor?.close()
    return filePath!!
}
