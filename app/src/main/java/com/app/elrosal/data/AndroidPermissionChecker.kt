package com.app.elrosal.data

import android.Manifest
import android.app.Application
import androidx.core.content.ContextCompat
import com.app.data.PermissionChecker


class AndroidPermissionChecker (private val application: Application) : PermissionChecker {

    override fun check(permission: PermissionChecker.Permission): Boolean =
        ContextCompat.checkSelfPermission(
            application,
            permission.toAndroidId()
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
}

private fun PermissionChecker.Permission.toAndroidId() = when (this) {
    PermissionChecker.Permission.CALL_PHONE -> Manifest.permission.CALL_PHONE
}