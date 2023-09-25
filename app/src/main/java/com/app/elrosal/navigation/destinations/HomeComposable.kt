package com.app.elrosal.navigation.destinations

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.elrosal.MainViewModel
import com.app.elrosal.model.Routes
import com.app.elrosal.ui.home.view.HomeScreen

fun NavGraphBuilder.homeComposable(
    mainViewModel: MainViewModel,
    navigateToProductScreen: (String) -> Unit,
    permissionCall: () -> Unit
) {
    composable(
        route = Routes.Home.route,
        enterTransition = {
            EnterTransition.None
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
        }
    ) {
        HomeScreen(
            mainViewModel = mainViewModel,
            navigateToProductScreen = navigateToProductScreen,
            permissionCall = permissionCall
        )
    }
}