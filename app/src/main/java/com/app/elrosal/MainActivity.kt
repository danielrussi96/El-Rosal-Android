package com.app.elrosal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.elrosal.navigation.SetupNavigation
import com.app.elrosal.ui.theme.ElRosalTheme
import java.util.Date


class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            ElRosalTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                )

                navHostController = rememberNavController()
                SetupNavigation(
                    navController = navHostController,
                    mainViewModel = mainViewModel
                )
            }
        }
    }
}