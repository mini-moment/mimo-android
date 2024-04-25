package com.mimo.android.presentation.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import timber.log.Timber

//사진 권한 허용
fun requestMapPermission(complete: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        TedPermission.create()
            .setDeniedMessage("파일 권한을 허용해주세요.")// 권한이 없을 때 띄워주는 Dialog Message
            .setDeniedCloseButtonText("닫기")
            .setGotoSettingButtonText("설정")
            .setPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )// 얻으려는 권한(여러개 가능)
            .setPermissionListener(object : PermissionListener {
                //권한이 허용됐을 때
                override fun onPermissionGranted() {
                    Timber.d("권한 허용 완료!")
                    complete()
                }

                //권한이 거부됐을 때
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Timber.d("권한 허용이 거부됨")
                }
            })
            .check()
    }
}


fun checkLocationPermission(context : Context){
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }
}