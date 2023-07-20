package com.app.elrosal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.elrosal.MainViewModel
import com.app.elrosal.model.Routes
import com.app.elrosal.navigation.destinations.detailComposable
import com.app.elrosal.navigation.destinations.homeComposable
import com.app.elrosal.navigation.destinations.productsComposable

@Composable
fun SetupNavigation(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    val screens = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(navController = navController, startDestination = Routes.Home.route) {
        homeComposable(mainViewModel = mainViewModel, navigateToProductScreen = screens.products)
        productsComposable(mainViewModel = mainViewModel, navigateToDetailScreen = screens.detail)
        detailComposable(mainViewModel = mainViewModel)
    }
}