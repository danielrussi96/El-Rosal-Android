package com.app.elrosal

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.domain.categories.Category
import com.app.domain.categories.SubCategories
import com.app.domain.products.Product
import com.app.domain.products.Products
import com.app.elrosal.model.Routes
import com.app.elrosal.navigation.SetupNavigation
import com.app.elrosal.ui.home.view.HomeScreen
import com.app.elrosal.ui.products.ProductsScreen
import com.app.elrosal.ui.theme.ElRosalTheme


class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var navHostController: NavHostController

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

        val listProducts = Products(
            listProducts = listOf(
                Product(
                    categoryId = "1",
                    description = "description",
                    details = emptyList(),
                    id = "1",
                    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
                    name = "Paw Patrol",
                    position = 1,
                    subCategoryId = "",
                ),
                Product(
                    categoryId = "1",
                    description = "description",
                    details = emptyList(),
                    id = "2",
                    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
                    name = "Paw Patrol",
                    position = 1,
                    subCategoryId = "",
                ),
                Product(
                    categoryId = "1",
                    description = "description",
                    details = emptyList(),
                    id = "3",
                    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
                    name = "Paw Patrol",
                    position = 1,
                    subCategoryId = "",
                ),
                Product(
                    categoryId = "1",
                    description = "description",
                    details = emptyList(),
                    id = "4",
                    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FProducts%2Fpaw_patrol_300x300.webp?alt=media&token=3b8b5b1a-5b0a-4b0a-9b0a-5b0a4b0a9b0a",
                    name = "Paw Patrol",
                    position = 1,
                    subCategoryId = "",
                ),
                Product(
                    categoryId = "1",
                    description = "description",
                    details = emptyList(),
                    id = "5",
                    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FProducts%2Fpaw_patrol_300x300.webp?alt=media&token=3b8b5b1a-5b0a-4b0a-9b0a-5b0a4b0a9b0a",
                    name = "Paw Patrol",
                    position = 1,
                    subCategoryId = "",
                ),
                Product(
                    categoryId = "1",
                    description = "description",
                    details = emptyList(),
                    id = "6",
                    image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FProducts%2Fpaw_patrol_300x300.webp?alt=media&token=3b8b5b1a-5b0a-4b0a-9b0a-5b0a4b0a9b0a",
                    name = "Paw Patrol",
                    position = 1,
                    subCategoryId = "",
                )
            ),
            statusCode = 200,
            totalPages = 1
        )
        val listCategories = listOf(
            Category(
                id = "1",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
                name = "Paw Patrol",
                position = 0
            ),
            Category(
                id = "2",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
                name = "Paw Patrol 2",
                position = 1
            ),
            Category(
                id = "3",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
                name = "Paw Patrol 3",
                position = 2
            )
        )
        val subCategories = SubCategories("Infantil", listCategories)



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
                    subCategories = subCategories,
                    products = listProducts
                )
            }
        }
    }
}