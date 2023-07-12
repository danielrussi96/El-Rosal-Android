package com.app.elrosal.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.app.elrosal.MainViewModel
import com.app.elrosal.model.Routes
import com.app.elrosal.ui.home.view.HomeScreen

fun NavGraphBuilder.homeComposable(
    mainViewModel: MainViewModel,
    navigateToProductScreen: (String) -> Unit
) {
    composable(Routes.Home.route) {
        HomeScreen(mainViewModel = mainViewModel, navigateToProductScreen = navigateToProductScreen)
    }
}