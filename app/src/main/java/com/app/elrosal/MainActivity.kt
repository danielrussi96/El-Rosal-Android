package com.app.elrosal

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.elrosal.navigation.SetupNavigation
import com.app.elrosal.ui.common.permission.PermissionRequester
import com.app.elrosal.ui.theme.ElRosalTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var navHostController: NavHostController

    private lateinit var mainState: MainState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        mainState = buildMainState(
            lifecycleScope,
            PermissionRequester(this, Manifest.permission.CALL_PHONE)
        )


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
                    mainViewModel = mainViewModel,
                    permissionCall = {
                        requestPermission()
                    }
                )
            }
        }
    }


    private fun requestPermission() {
        mainState.requestLocationPermission {isGranted ->
            if (isGranted) {
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:+573128031680")))
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}