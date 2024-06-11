package com.mimo.android.presentation.util

import android.content.Context
import android.location.Geocoder
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale


fun Context.locationToAddress(latitude: Double, longitude: Double, success: (String) -> Unit) {

    val geocoder = Geocoder(this@locationToAddress, Locale.KOREAN)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        geocoder.getFromLocation(latitude, longitude, 1) { address ->
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val result =
                        "${address[0].locality} ${address[0].thoroughfare} ${address[0].featureName}"
                    success(result)
                }
            }
        }
    }
}