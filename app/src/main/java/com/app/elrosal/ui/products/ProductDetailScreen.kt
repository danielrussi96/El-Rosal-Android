package com.app.elrosal.ui.products

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
import androidx.compose.ui.tooling.preview.Preview
import com.app.domain.products.Detail
import com.app.domain.products.Product
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
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.utils.ConstantsViews.DELAY_5000


@Composable
fun ProductDetailScreen(product: Product, products: List<Product>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        content = {
            item {
                TitleContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = PADDING_16), title = "Detalle de producto"
                )
                ProductDetailContent(product = product)
                SubTitleContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = PADDING_16, top = PADDING_16, bottom = PADDING_8),
                    subTitle = "Productos recomendados"
                )
            }
            items(products, key = { product ->
                product.id
            }) { product ->
                RecommendedProductsContent(product = product)
            }
        })
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailContent(product: Product) {
    val pagerState = rememberPagerState()
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
                TitleProducts(titleProducts = product.name)
                DescriptionProducts(
                    modifier = Modifier.padding(PADDING_16),
                    descriptionProducts = product.description
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
                                    if (currentPage < product.details.size - 1) currentPage + 1 else 0
                                delay(DELAY_5000)
                                pagerState.animateScrollToPage(target)
                            }
                        }
                    }
                }
        }


        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(), pageCount = product.details.size, state = pagerState
        ) { index ->

            val pageOffset =
                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.75f else 1f,
                animationSpec = tween(durationMillis = 300),
                label = "imageSize"
            )

            AsyncImagePainter(
                url = product.details[index].image
            ) { painter ->
                Image(
                    painter = painter,
                    contentDescription = product.details[index].title.orEmpty(),
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailScreenPreview() {
    val detail = listOf(
        Detail(
            image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
            "1"
        ),
        Detail(
            image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
            "2"
        )
    )
    val product = Product(
        categoryId = "1",
        description = "description",
        details = detail,
        id = "1",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
        name = "Paw Patrol",
        position = 1,
        subCategoryId = "",
    )
    val products = listOf(product)
    ProductDetailScreen(product = product, products = products)
}