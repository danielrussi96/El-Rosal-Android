package com.app.elrosal

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.elrosal.ui.home.view.HomeScreen
import com.app.elrosal.ui.theme.ElRosalTheme


class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()


        mainViewModel.getSubCategories("85PXtMUmI3pIHUQ33P1D") {
            Log.d("Daniel", it.toString())
        }

        mainViewModel.productsCategories("0SGpe0A0jOyXySF9Wb6p") {
            Log.d("Daniel", it.toString())
        }

        

        setContent {
            ElRosalTheme {
                HomeScreen(mainViewModel = mainViewModel)
            }
        }
    }
}