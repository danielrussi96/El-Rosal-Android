package com.app.elrosal.ui.products.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.common.TitleContent
import com.app.elrosal.utils.ConstantsViews.COUNT_GRID_COLUMNS_2
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.domain.products.Product
import com.app.elrosal.MainViewModel
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.products.ProductsUiState
import com.app.elrosal.ui.products.SubCategoriesUiState
import com.app.elrosal.ui.theme.CARD_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION

import com.app.elrosal.ui.theme.IMAGE_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import kotlinx.coroutines.flow.Flow

@Composable
fun ProductsScreen(
    mainViewModel: MainViewModel,
    id: String
) {
    var categoryName by remember { mutableStateOf("") }
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<SubCategoriesUiState>(
        initialValue = SubCategoriesUiState.Loading,
        key1 = lifecycle,
        key2 = mainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainViewModel.uiStateSubCategories.collect { subCategoriesUiState ->
                value = subCategoriesUiState
            }
        }
    }

    mainViewModel.getSubCategories(id = id)

    when (uiState) {

        is SubCategoriesUiState.Loading -> {}

        is SubCategoriesUiState.Success -> {
            val data = (uiState as SubCategoriesUiState.Success).subCategories
            Column(modifier = Modifier.background(colorScheme.background)) {
                TitleContent(
                    modifier = Modifier
                        .padding(vertical = PADDING_16)
                        .align(Alignment.CenterHorizontally), title = data.name
                )
                SubCategoriesContent(subCategories = data.subCategories) { category ->
                    categoryName = category.name
                    mainViewModel.setSubCategoryId(id = category.id)
                }
                ProductsContent(
                    categoryName = categoryName,
                    mainViewModel = mainViewModel
                )
            }
        }

        is SubCategoriesUiState.Error -> {
            Log.d("Daniel", "ProductsScreen: Error")
        }
    }

}

@Composable
fun ProductsContent(
    categoryName: String,
    mainViewModel: MainViewModel
) {

    val lazyPagingItems =
        mainViewModel.products.collectAsLazyPagingItems()
    val products = lazyPagingItems.itemSnapshotList.distinctBy { product ->
        product?.id
    }

    SubTitleContent(
        modifier = Modifier.padding(
            bottom = PADDING_16,
            start = PADDING_16,
            end = PADDING_16
        ),
        subTitle = categoryName
    )

    LazyVerticalGrid(columns = GridCells.Fixed(COUNT_GRID_COLUMNS_2), content = {
        items(products, key = { item ->
            item?.id ?: ""
        }) { product ->
            if (product != null) ProductItem(product = product)
        }

    })
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_8)
            .height(CARD_HEIGHT_PRODUCTS),
        colors = cardColors(
            containerColor = colorScheme.surface,
        ),
        elevation = cardElevation(CATEGORIES_ELEVATION),
        shape = RoundedCornerShape(ROUND_CORNERS_16)
    ) {

        Box(
            modifier = Modifier
                .padding(PADDING_8)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = product.name,
                color = colorScheme.secondary,
                style = typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            AsyncImagePainter(url = product.image) { painter ->
                Image(
                    painter = painter,
                    contentDescription = product.name,
                    modifier = Modifier
                        .width(IMAGE_HEIGHT_PRODUCTS)
                        .height(IMAGE_HEIGHT_PRODUCTS)
                        .align(Alignment.TopCenter)
                )
            }
        }

    }
}


