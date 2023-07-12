package com.app.elrosal.ui.products

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.app.domain.categories.CategoriesItem

import com.app.elrosal.ui.common.CategoriesItem

@Composable
fun SubCategoriesContent(
    categories: List<CategoriesItem>,
    navigationController: NavHostController
) {
    LazyRow(content = {
        items(categories, key = { item ->
            item.id
        }) { category ->
            //CategoriesItem(categoriesItem = category, navigationController = navigationController)
        }
    })
}


