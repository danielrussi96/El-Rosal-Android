package com.app.elrosal.ui.products

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.domain.categories.CategoriesItem
import com.app.domain.categories.Category

import com.app.elrosal.ui.common.CategoriesItem
import com.app.elrosal.ui.theme.ElRosalTheme

@Composable
fun SubCategoriesContent(categories: List<CategoriesItem>) {
    LazyRow(content = {
        items(categories, key = { item ->
            item.id
        }) { category ->
            CategoriesItem(categoriesItem = category)
        }
    })
}


