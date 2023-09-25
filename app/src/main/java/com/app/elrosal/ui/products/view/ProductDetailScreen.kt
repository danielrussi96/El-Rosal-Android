package com.app.elrosal.ui.products.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.app.elrosal.ui.common.DescriptionProducts
import com.app.elrosal.ui.common.TitleContent
import com.app.elrosal.ui.common.TitleProducts
import com.app.elrosal.ui.theme.CARD_HEIGHT_PRODUCT_DETAIL
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_PRODUCT_DETAIL
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_24
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.app.domain.details.DetailProduct
import com.app.elrosal.MainViewModel
import com.app.elrosal.R
import com.app.elrosal.ui.common.AnimatedShimmer
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.products.DetailProductUiState
import com.app.elrosal.ui.theme.CARD_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.HEIGHT_16
import com.app.elrosal.ui.theme.HEIGHT_160
import com.app.elrosal.ui.theme.HEIGHT_32
import com.app.elrosal.ui.theme.HEIGHT_40
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_SUBCATEGORIES
import com.app.elrosal.ui.theme.PADDING_64
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.ui.theme.ROUND_CORNERS_8
import com.app.elrosal.ui.theme.WIDTH_176
import com.app.elrosal.ui.theme.WIDTH_192
import com.app.elrosal.ui.theme.WIDTH_4
import com.app.elrosal.utils.ConstantsViews
import com.app.elrosal.utils.ConstantsViews.DELAY_5000
import kotlinx.coroutines.launch


@Composable
fun ProductDetailScreen(mainViewModel: MainViewModel, id: String) {

    LaunchedEffect(id) {
        mainViewModel.getDetailProduct(id = id)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val uiState by produceState<DetailProductUiState>(
        initialValue = DetailProductUiState.Loading,
        key1 = lifecycle,
        key2 = mainViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainViewModel.uiStateDetailProduct.collect { productDescription ->
                value = productDescription
            }
        }
    }

    when (uiState) {
        is DetailProductUiState.Loading -> {
            ProductDetailShimmer()
        }

        is DetailProductUiState.Success -> {
            val productDescription = (uiState as DetailProductUiState.Success).productDescription
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
                state = listState,
                content = {
                    item {
                        TitleContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = PADDING_16),
                            title = stringResource(id = R.string.detail_label)
                        )
                        ProductDetailContent(detailProduct = productDescription.detailProduct)
                        SubTitleContent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = PADDING_16, top = PADDING_16, bottom = PADDING_8),
                            subTitle = stringResource(id = R.string.recommended_label)
                        )
                    }
                    items(productDescription.recommendedProducts, key = { product ->
                        product.id
                    }) { product ->
                        RecommendedProductsContent(
                            recommendedProducts = product,
                            navigateToDetailScreen = { id ->
                                mainViewModel.getDetailProduct(id = id)
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            }
                        )
                    }
                })
        }

        is DetailProductUiState.Error -> {

        }
    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailContent(detailProduct: DetailProduct) {

    val pagerState = rememberPagerState { detailProduct.details.size }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(CARD_HEIGHT_PRODUCT_DETAIL)
            .padding(horizontal = PADDING_24)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = PADDING_64)
                .height(CARD_HEIGHT_PRODUCT_DETAIL),
            colors = cardColors(
                containerColor = colorScheme.surface
            ),
            elevation = cardElevation(CATEGORIES_ELEVATION),
            shape = RoundedCornerShape(ROUND_CORNERS_16),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(PADDING_16))
                TitleProducts(titleProducts = detailProduct.name)
                DescriptionProducts(
                    modifier = Modifier.padding(bottom = PADDING_16),
                    descriptionProducts = detailProduct.description
                )
            }


        }

        val isDraggedState = pagerState.interactionSource.collectIsDraggedAsState()
        LaunchedEffect(isDraggedState) {
            snapshotFlow { isDraggedState.value }
                .collectLatest { isDragged ->
                    if (!isDragged) {
                        while (true) {
                            with(pagerState) {
                                val target =
                                    if (currentPage < detailProduct.details.size - 1) currentPage + 1 else 0
                                delay(DELAY_5000)
                                pagerState.animateScrollToPage(target)
                            }
                        }
                    }
                }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            state = pagerState
        ) { index ->

            val pageOffset =
                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300),
                label = "imageSize"
            )

            AsyncImagePainter(
                url = detailProduct.details[index].image
            ) { painter ->
                Image(
                    painter = painter,
                    contentDescription = detailProduct.details[index].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IMAGE_HEIGHT_PRODUCT_DETAIL)
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                        }
                        .offset(y = (-24).dp)
                )
            }
        }

    }
}



@Composable
fun ProductDetailShimmer() {
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

            Spacer(
                modifier = Modifier
                    .width(CARD_HEIGHT_PRODUCT_DETAIL)
                    .height(CARD_HEIGHT_PRODUCT_DETAIL)
                    .clip(RoundedCornerShape(ROUND_CORNERS_8))
                    .background(brush)
            )

            Spacer(modifier = Modifier.height(HEIGHT_16))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = PADDING_16)) {
                Spacer(
                    modifier = Modifier
                        .width(WIDTH_192)
                        .height(HEIGHT_32)
                        .clip(RoundedCornerShape(ROUND_CORNERS_8))
                        .background(brush)
                )
            }

            Spacer(modifier = Modifier.height(HEIGHT_16))

            LazyColumn(
                modifier = Modifier.padding(horizontal = PADDING_16),
                verticalArrangement = Arrangement.spacedBy(PADDING_16),
                content = {
                    items(8) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(HEIGHT_160)
                                .clip(RoundedCornerShape(ROUND_CORNERS_8))
                                .background(brush)
                        )
                    }
                })
        }
    }
}

@Composable
@Preview
fun ProductDetailShimmerPreview() {
    ProductDetailShimmer()
}