package com.app.elrosal.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.elrosal.MainViewModel
import com.app.elrosal.model.Routes
import com.app.elrosal.ui.products.view.ProductDetailScreen
import com.app.elrosal.utils.Constants.DETAIL_ARGUMENT_KEY

fun NavGraphBuilder.detailComposable(
    mainViewModel: MainViewModel,
) {

    composable(
        route = "${Routes.Detail.route}/{id}",
        arguments = listOf(navArgument(DETAIL_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY).orEmpty()
        ProductDetailScreen(mainViewModel = mainViewModel, id = id)
    }
}