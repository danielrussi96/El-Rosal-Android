package com.app.elrosal.ui.home.view

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.app.elrosal.MainViewModel
import com.app.elrosal.R
import com.app.elrosal.ui.common.CategoriesItem
import com.app.elrosal.ui.common.CategoriesItemShimmer
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.home.CategoriesUiState
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_CATEGORIES
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_SUBCATEGORIES
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.utils.ConstantsViews.COUNT_GRID_COLUMNS_2

@Composable
fun CategoriesContent(
    mainViewModel: MainViewModel,
    navigateToProductScreen: (String) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<CategoriesUiState>(
        initialValue = CategoriesUiState.Loading,
        key1 = lifecycle,
        key2 = mainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainViewModel.uiStateCategories.collect { categoriesUiState ->
                value = categoriesUiState
            }
        }
    }

    when (uiState) {
        is CategoriesUiState.Error -> {

        }

        is CategoriesUiState.Loading -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(COUNT_GRID_COLUMNS_2),
                content = {
                    item(span = {
                        GridItemSpan(COUNT_GRID_COLUMNS_2)
                    }) {
                        SubTitleContent(
                            modifier = Modifier.padding(PADDING_16),
                            subTitle = stringResource(id = R.string.categories_label)
                        )
                    }
                    items(12) {
                        CategoriesItemShimmer()
                    }
                })
        }

        is CategoriesUiState.Success -> {
            val data = (uiState as CategoriesUiState.Success).categories
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(COUNT_GRID_COLUMNS_2),
                content = {
                    item(span = {
                        GridItemSpan(COUNT_GRID_COLUMNS_2)
                    }) {
                        SubTitleContent(
                            modifier = Modifier.padding(PADDING_16),
                            subTitle = stringResource(id = R.string.categories_label)
                        )
                    }
                    items(data, key = { item ->
                        item.id
                    }) { category ->
                        CategoriesItem(
                            category = category,
                            modifier = Modifier
                                .width(IMAGE_HEIGHT_CATEGORIES)
                                .height(IMAGE_HEIGHT_CATEGORIES),
                            navigateToProductScreen = navigateToProductScreen
                        )
                    }
                })
        }
    }
}


