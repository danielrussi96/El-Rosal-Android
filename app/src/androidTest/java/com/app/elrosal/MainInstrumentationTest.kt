package com.app.elrosal

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.app.data.datasource.CategoriesRemoteDataSource
import com.app.elrosal.ui.home.view.HomeScreen
import com.app.library.data.database.ElRosalDatabase
import com.app.library.shared.cache.DriverFactory
import com.app.library.shared.cache.ElRosalDatabaseFactory
import com.app.library.shared.network.KtorClientFactory
import io.ktor.client.HttpClient
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.loadKoinModules
import org.koin.test.get
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest


class MainInstrumentationTest : AutoCloseKoinTest() {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mainViewModel: MainViewModel


    private lateinit var elRosalDatabase: ElRosalDatabase


    private lateinit var categoriesRemoteDataSource: CategoriesRemoteDataSource


    private lateinit var httpClient: HttpClient


    @Before
    fun setup() {
        val androidModule = module {
            viewModelOf(::MainViewModel)
        }
        val driverModule = module {
            single {
                DriverFactory(get())
            }

            single {
                ElRosalDatabaseFactory(get()).createDatabase()
            }

            single {
                KtorClientFactory().build()
            }
        }

        loadKoinModules(androidModule + driverModule)

        mainViewModel = get()

        elRosalDatabase = get()

        categoriesRemoteDataSource = get()

        httpClient = get()
    }

    @Test
    fun clickToCategoryNavigateToDetail() = runTest {

        composeTestRule.setContent {
            HomeScreen(
                mainViewModel = mainViewModel,
                navigateToProductScreen = {},
                permissionCall = {}
            )
        }

        composeTestRule.onNodeWithTag("categories_content").onChildAt(0).performClick()

        composeTestRule.onNodeWithText("Infantil")

    }


}

