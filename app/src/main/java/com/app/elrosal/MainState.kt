package com.app.elrosal

import com.app.elrosal.ui.common.permission.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun buildMainState(
    scope: CoroutineScope,
    permissionRequester: PermissionRequester,
) = MainState(scope, permissionRequester)

class MainState(
    private val scope: CoroutineScope,
    private val permissionRequester: PermissionRequester
) {

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = permissionRequester.request()
            afterRequest(result)
        }
    }

}