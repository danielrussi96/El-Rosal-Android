package com.app.elrosal.ui.products.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.app.domain.products.Product
import com.app.elrosal.MainViewModel
import com.app.elrosal.ui.common.AnimatedShimmer
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.products.SubCategoriesUiState
import com.app.elrosal.ui.theme.CARD_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.ElRosalTheme
import com.app.elrosal.ui.theme.HEIGHT_16
import com.app.elrosal.ui.theme.HEIGHT_32
import com.app.elrosal.ui.theme.HEIGHT_40

import com.app.elrosal.ui.theme.IMAGE_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_SUBCATEGORIES
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import com.app.elrosal.ui.theme.ROUND_CORNERS_8
import com.app.elrosal.ui.theme.WIDTH_176
import com.app.elrosal.ui.theme.WIDTH_4

@Composable
fun ProductsScreen(
    mainViewModel: MainViewModel,
    id: String,
    navigateToDetailScreen: (String) -> Unit
) {

    LaunchedEffect(key1 = id) {
        mainViewModel.getSubCategories(id = id)
    }

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

    when (uiState) {

        is SubCategoriesUiState.Loading -> {
            ProductsShimmer()
        }

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
                    mainViewModel = mainViewModel,
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
        }

        is SubCategoriesUiState.Error -> {}
    }

}

@Composable
fun ProductsContent(
    categoryName: String,
    mainViewModel: MainViewModel,
    navigateToDetailScreen: (String) -> Unit
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
            if (product != null) ProductItem(
                product = product,
                navigateToDetailScreen = navigateToDetailScreen
            )
        }

    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(product: Product, navigateToDetailScreen: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_8)
            .height(CARD_HEIGHT_PRODUCTS),
        colors = cardColors(
            containerColor = colorScheme.surface,
        ),
        elevation = cardElevation(CATEGORIES_ELEVATION),
        shape = RoundedCornerShape(ROUND_CORNERS_16),
        onClick = {
            navigateToDetailScreen(product.id)
        }
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


@Composable
fun ProductsShimmer() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedShimmer { brush ->

            Spacer(modifier = Modifier.height(HEIGHT_16))

            Spacer(
                modifier = Modifier
                    .width(WIDTH_176)
                    .height(HEIGHT_40)
                    .clip(RoundedCornerShape(ROUND_CORNERS_8))
                    .background(brush)
            )

            Spacer(modifier = Modifier.height(HEIGHT_16))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(PADDING_16)) {

                item {
                    Spacer(modifier = Modifier.width(WIDTH_4))
                }

                items(4) {
                    Spacer(
                        modifier = Modifier
                            .width(IMAGE_HEIGHT_SUBCATEGORIES)
                            .height(IMAGE_HEIGHT_SUBCATEGORIES)
                            .clip(RoundedCornerShape(ROUND_CORNERS_8))
                            .background(brush)
                    )
                }

                item {
                    Spacer(modifier = Modifier.width(WIDTH_4))
                }
            }

            Spacer(modifier = Modifier.height(HEIGHT_16))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PADDING_16)) {
                Spacer(
                    modifier = Modifier
                        .width(WIDTH_176)
                        .height(HEIGHT_32)
                        .clip(RoundedCornerShape(ROUND_CORNERS_8))
                        .background(brush)
                )
            }

            Spacer(modifier = Modifier.height(HEIGHT_16))

            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = PADDING_16),
                horizontalArrangement = Arrangement.spacedBy(PADDING_16),
                verticalArrangement = Arrangement.spacedBy(PADDING_16),
                columns = GridCells.Fixed(COUNT_GRID_COLUMNS_2),
                content = {
                    items(8) {
                        Spacer(
                            modifier = Modifier
                                .width(CARD_HEIGHT_PRODUCTS)
                                .height(CARD_HEIGHT_PRODUCTS)
                                .clip(RoundedCornerShape(ROUND_CORNERS_8))
                                .background(brush)
                        )
                    }
                })
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ProductsShimmerPreview() {
    ElRosalTheme {
        ProductsShimmer()
    }
}

