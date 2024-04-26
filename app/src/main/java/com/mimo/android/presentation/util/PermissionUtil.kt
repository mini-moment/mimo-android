package com.mimo.android.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.mimo.android.R
import timber.log.Timber

fun Context.requestMapPermission(complete: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        TedPermission.create()
            .setDeniedMessage(this.getString(R.string.permission_denied_message)) // 권한이 없을 때 띄워주는 Dialog Message
            .setDeniedCloseButtonText(this.getString(R.string.permission_denied_closed_button_message))
            .setGotoSettingButtonText(this.getString(R.string.permission_go_to_setting_button_message))
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() { // 권한이 허용됐을 때
                    Timber.d("권한 허용 완료!")
                    complete()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { // 권한이 거부됐을 때
                    Timber.d("권한 허용이 거부됨")
                }
            })
            .check()
    }
}

fun checkLocationPermission(context: Context) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
}
