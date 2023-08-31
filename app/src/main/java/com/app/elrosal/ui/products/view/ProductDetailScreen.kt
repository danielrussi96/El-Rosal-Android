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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.app.elrosal.ui.theme.PADDING_96
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.app.domain.details.DetailProduct
import com.app.elrosal.MainViewModel
import com.app.elrosal.R
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.products.DetailProductUiState
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.utils.ConstantsViews.DELAY_5000


@Composable
fun ProductDetailScreen(mainViewModel: MainViewModel, id: String) {

    LaunchedEffect(id) {
        mainViewModel.getDetailProduct(id = id)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

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

        }

        is DetailProductUiState.Success -> {
            val productDescription = (uiState as DetailProductUiState.Success).productDescription
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.background),
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
                            })
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
                .padding(top = PADDING_96)
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
                TitleProducts(titleProducts = detailProduct.name)
                DescriptionProducts(
                    modifier = Modifier.padding(PADDING_16),
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
            modifier = Modifier
                .fillMaxWidth(), state = pagerState
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
                        .width(IMAGE_HEIGHT_PRODUCT_DETAIL)
                        .height(IMAGE_HEIGHT_PRODUCT_DETAIL)
                        .graphicsLayer {
                            scaleX = imageSize
                            scaleY = imageSize
                        }
                )
            }
        }

    }
}


