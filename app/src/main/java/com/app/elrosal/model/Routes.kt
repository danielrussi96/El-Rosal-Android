package com.app.elrosal.model

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Products : Routes("products")

    object Detail : Routes("detail")
}