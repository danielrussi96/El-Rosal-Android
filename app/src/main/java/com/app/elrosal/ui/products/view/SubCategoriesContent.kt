package com.app.elrosal.ui.products.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.domain.categories.remote.Category
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.theme.CARD_HEIGHT_CATEGORIES
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION
import com.app.elrosal.ui.theme.HEIGHT_160
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_SUBCATEGORIES
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_40
import com.app.elrosal.ui.theme.ROUND_CORNERS_16
import com.app.elrosal.ui.theme.WIDTH_176

@Composable
fun SubCategoriesContent(
    subCategories: List<Category>,
    onSubCategorySelected: (Category) -> Unit
) {

    val itemSelection = remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        if (subCategories.isNotEmpty()) {
            onSubCategorySelected(subCategories[0])
        }
    }

    LazyRow(content = {
        itemsIndexed(subCategories, key = { _, item ->
            item.id
        }) { index, subCategory ->
            SubCategoriesItem(
                modifier = Modifier
                    .width(IMAGE_HEIGHT_SUBCATEGORIES)
                    .height(IMAGE_HEIGHT_SUBCATEGORIES),
                item = subCategory,
                itemSelection = itemSelection,
                index = index,
                onSubCategorySelected = onSubCategorySelected
            )
        }
    })
}

@Composable
fun SubCategoriesItem(
    modifier: Modifier,
    item: Category,
    itemSelection: MutableState<Int>,
    index: Int,
    onSubCategorySelected: (Category) -> Unit,
) {

    Box(
        modifier = Modifier
            .width(WIDTH_176)
            .height(HEIGHT_160)
            .padding(bottom = PADDING_16)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = PADDING_16, end = PADDING_16, top = PADDING_40)
                .height(CARD_HEIGHT_CATEGORIES)
                .align(Alignment.BottomCenter)
                .selectable(
                    selected = itemSelection.value == index,
                    onClick = {
                        itemSelection.value = if (itemSelection.value == index) -1 else index
                        onSubCategorySelected(item)
                    },
                    enabled = itemSelection.value != index
                ),
            colors = CardDefaults.cardColors(
                containerColor = if (itemSelection.value == index)
                    colorScheme.primaryContainer else colorScheme.surface,
            ),
            elevation = CardDefaults.cardElevation(CATEGORIES_ELEVATION),
            shape = RoundedCornerShape(ROUND_CORNERS_16)
        ) {

        }

        AsyncImagePainter(
            url = item.image
        ) { painter ->
            Image(
                painter = painter,
                contentDescription = item.name,
                modifier = modifier.align(Alignment.TopCenter)
            )
        }

    }
}






