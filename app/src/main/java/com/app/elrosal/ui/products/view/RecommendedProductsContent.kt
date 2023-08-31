package com.app.elrosal.ui.products.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.domain.details.RecommendedProduct
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.common.DescriptionProducts
import com.app.elrosal.ui.common.TitleProducts
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import com.app.elrosal.utils.ConstantsViews.WEIGHT_1F
import com.app.elrosal.utils.ConstantsViews.WEIGHT_2F

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendedProductsContent(
    recommendedProducts: RecommendedProduct,
    navigateToDetailScreen: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_8),
        colors = cardColors(colorScheme.surface),
        elevation = cardElevation(CATEGORIES_ELEVATION),
        shape = RoundedCornerShape(ROUND_CORNERS_16),
        onClick = { navigateToDetailScreen(recommendedProducts.id) }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_16)
        ) {

            AsyncImagePainter(url = recommendedProducts.image) { painter ->
                Image(
                    modifier = Modifier
                        .weight(WEIGHT_1F)
                        .align(Alignment.CenterVertically),
                    painter = painter,
                    contentDescription = recommendedProducts.name
                )
            }

            Column(
                modifier = Modifier.weight(WEIGHT_2F),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TitleProducts(titleProducts = recommendedProducts.name)
                DescriptionProducts(
                    modifier = Modifier.padding(PADDING_16),
                    descriptionProducts = recommendedProducts.description
                )
            }
        }

    }
}

