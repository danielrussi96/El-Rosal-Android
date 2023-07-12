package com.app.elrosal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.app.domain.categories.CategoriesItem
import com.app.elrosal.ui.theme.CARD_HEIGHT_CATEGORIES
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.HEIGHT_16
import com.app.elrosal.ui.theme.HEIGHT_200
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_CATEGORIES
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_CATEGORIES_SHIMMER
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_40
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import com.app.elrosal.ui.theme.WIDTH_150

@Composable
fun CategoriesItem(
    categoriesItem: CategoriesItem,
    navigateToProductScreen: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .width(WIDTH_150)
            .height(HEIGHT_200)
            .padding(bottom = PADDING_16)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = PADDING_16, end = PADDING_16, top = PADDING_40)
                .height(CARD_HEIGHT_CATEGORIES)
                .align(Alignment.BottomCenter)
                .clickable {
                    navigateToProductScreen(categoriesItem.id)
                },
            colors = cardColors(
                containerColor = colorScheme.surface
            ),
            elevation = cardElevation(CATEGORIES_ELEVATION),
            shape = RoundedCornerShape(ROUND_CORNERS_16)
        ) {
            Box(
                modifier = Modifier
                    .padding(PADDING_16)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = categoriesItem.name,
                    color = colorScheme.secondary,
                    style = typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        AsyncImagePainter(
            url = categoriesItem.image
        ) { painter ->
            Image(
                painter = painter,
                contentDescription = categoriesItem.name,
                modifier = Modifier
                    .width(IMAGE_HEIGHT_CATEGORIES)
                    .height(IMAGE_HEIGHT_CATEGORIES)
                    .align(Alignment.TopCenter)
            )
        }

    }
}

@Composable
fun CategoriesItemShimmer() {
    AnimatedShimmer { brush ->
        Box(
            modifier = Modifier
                .width(WIDTH_150)
                .height(HEIGHT_200)
                .padding(bottom = PADDING_16)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING_16, end = PADDING_16, top = PADDING_40)
                    .height(CARD_HEIGHT_CATEGORIES),
                colors = cardColors(
                    containerColor = colorScheme.surface
                ),
                elevation = cardElevation(CATEGORIES_ELEVATION),
                shape = RoundedCornerShape(ROUND_CORNERS_16)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.height(PADDING_16))
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(HEIGHT_16)
                            .padding(horizontal = PADDING_16)
                            .clip(RoundedCornerShape(ROUND_CORNERS_16))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(PADDING_16))
                }
            }
            Spacer(
                modifier = Modifier
                    .size(IMAGE_HEIGHT_CATEGORIES_SHIMMER)
                    .clip(RoundedCornerShape(ROUND_CORNERS_16))
                    .background(brush)
                    .align(Alignment.TopCenter)
            )
        }

    }

}


