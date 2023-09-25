package com.app.elrosal.navigation.destinations

import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.elrosal.MainViewModel
import com.app.elrosal.model.Routes
import com.app.elrosal.ui.products.view.ProductsScreen
import com.app.elrosal.utils.Constants.PRODUCTS_ARGUMENT_KEY

fun NavGraphBuilder.productsComposable(
    mainViewModel: MainViewModel,
    navigateToDetailScreen: (String) -> Unit
) {
    composable(
        route = "${Routes.Products.route}/{id}",
        enterTransition = {
            slideInHorizontally { initialOffsetX ->
                initialOffsetX + 1000
            } + fadeIn(animationSpec = tween(1000))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 },
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(1000))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 },
                animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(1000))
        },
        popExitTransition = {
            ExitTransition.None
        },
        arguments = listOf(navArgument(PRODUCTS_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBackStackEntry ->
        val id = navBackStackEntry.arguments?.getString(PRODUCTS_ARGUMENT_KEY).orEmpty()
        ProductsScreen(
            mainViewModel = mainViewModel,
            id = id,
            navigateToDetailScreen = navigateToDetailScreen
        )
    }
}