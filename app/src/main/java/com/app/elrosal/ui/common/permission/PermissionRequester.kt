package com.app.elrosal.ui.common.permission

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionRequester(activity: ComponentActivity, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}
    private val launcher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onRequest(isGranted)
        }

    suspend fun request(): Boolean =
        suspendCancellableCoroutine { continuation ->
            onRequest = { isGranted ->
                continuation.resume(isGranted)
            }
            launcher.launch(permission)
        }
}