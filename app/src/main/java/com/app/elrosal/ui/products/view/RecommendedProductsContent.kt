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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.domain.products.Product
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.common.DescriptionProducts
import com.app.elrosal.ui.common.TitleProducts
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import com.app.elrosal.utils.ConstantsViews.WEIGHT_1F
import com.app.elrosal.utils.ConstantsViews.WEIGHT_2F

@Composable
fun RecommendedProductsContent(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_8),
        colors = cardColors(colorScheme.secondary),
        elevation = cardElevation(CATEGORIES_ELEVATION),
        shape = RoundedCornerShape(ROUND_CORNERS_16)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PADDING_16)
        ) {

            AsyncImagePainter(url = product.image) { painter ->
                Image(
                    modifier = Modifier
                        .weight(WEIGHT_1F)
                        .align(Alignment.CenterVertically),
                    painter = painter,
                    contentDescription = product.name
                )
            }

            Column(
                modifier = Modifier.weight(WEIGHT_2F),
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

    }
}


@Composable
@Preview(showBackground = true)
fun RecommendedProductsContentPreview() {
    val product = Product(
        categoryId = "1",
        description = "description",
        details = emptyList(),
        id = "1",
        image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
        name = "Paw Patrol",
        position = 1,
        subCategoryId = "",
    )
    RecommendedProductsContent(product = product)
}