package com.app.elrosal.model

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Products : Routes("products")

    data object Detail : Routes("detail")
}