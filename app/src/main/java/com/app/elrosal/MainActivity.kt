package com.app.elrosal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
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
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:+573128031680")))
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

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
                    mainViewModel = mainViewModel,
                    permissionCall = {
                        requestPermission()
                    }
                )
            }
        }
    }


    private fun requestPermission(){
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) -> {
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:+573128031680")))
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        }
    }
}