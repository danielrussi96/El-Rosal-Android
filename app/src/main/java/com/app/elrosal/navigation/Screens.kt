package com.app.elrosal.navigation

import androidx.navigation.NavController
import com.app.elrosal.model.Routes

class Screens(navController: NavController) {

    val products: (String) -> Unit = { id ->
        navController.navigate("${Routes.Products.route}/$id")
    }

    val detail: (String) -> Unit = { id ->
        navController.navigate("${Routes.Detail.route}/$id")
    }
}