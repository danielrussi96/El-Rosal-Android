package com.app.elrosal.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.domain.products.Products
import com.app.elrosal.MainViewModel
import com.app.elrosal.model.Routes
import com.app.elrosal.ui.products.view.ProductsScreen
import com.app.elrosal.utils.Constants.PRODUCTS_ARGUMENT_KEY

fun NavGraphBuilder.productsComposable(
    mainViewModel: MainViewModel
) {
    composable(
        route = "${Routes.Products.route}/{id}",
        arguments = listOf(navArgument(PRODUCTS_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getString(PRODUCTS_ARGUMENT_KEY).orEmpty()
        ProductsScreen(
            mainViewModel = mainViewModel,
            id = id
        )
    }
}