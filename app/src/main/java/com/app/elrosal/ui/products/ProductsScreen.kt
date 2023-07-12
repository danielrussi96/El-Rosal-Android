package com.app.elrosal.ui.products

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.domain.categories.Category
import com.app.domain.categories.SubCategories
import com.app.domain.products.Products
import com.app.elrosal.ui.common.SubTitleContent
import com.app.elrosal.ui.common.TitleContent
import com.app.elrosal.ui.theme.ElRosalTheme
import com.app.elrosal.utils.ConstantsViews.COUNT_GRID_COLUMNS_2
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.ui.text.style.TextOverflow
import com.app.domain.products.Product
import com.app.elrosal.ui.common.AsyncImagePainter
import com.app.elrosal.ui.theme.CARD_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.CATEGORIES_ELEVATION

import com.app.elrosal.ui.theme.IMAGE_HEIGHT_PRODUCTS
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.ui.theme.PADDING_8
import com.app.elrosal.ui.theme.ROUND_CORNERS_16

@Composable
fun ProductsScreen(
    subCategories: SubCategories,
    products: Products,
    id: String,
) {
    Log.d("Daniel", "ProductsScreen: $id")
    Column(modifier = Modifier.background(colorScheme.background)) {
        TitleContent(
            modifier = Modifier
                .padding(top = PADDING_16)
                .align(Alignment.CenterHorizontally), title = subCategories.name
        )
        //SubCategoriesContent(categories = subCategories.subCategories)
        ProductsContent(products = products)
    }
}

@Composable
fun ProductsContent(products: Products) {
    LazyVerticalGrid(columns = GridCells.Fixed(COUNT_GRID_COLUMNS_2), content = {
        item(span = {
            GridItemSpan(COUNT_GRID_COLUMNS_2)
        }) {
            SubTitleContent(
                modifier = Modifier.padding(PADDING_16),
                subTitle = "Categorias"
            )
        }

        items(products.listProducts, key = { item ->
            item.id
        }) { product ->
            ProductItem(product = product)
        }

    })
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_8)
            .height(CARD_HEIGHT_PRODUCTS),
        colors = cardColors(
            containerColor = colorScheme.surface,
        ),
        elevation = cardElevation(CATEGORIES_ELEVATION),
        shape = RoundedCornerShape(ROUND_CORNERS_16)
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
@Preview(showBackground = true, showSystemUi = true)
fun ProductsScreenPreview() {

    val listProducts = Products(
        listProducts = listOf(
            Product(
                categoryId = "1",
                description = "description",
                details = emptyList(),
                id = "1",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
                name = "Paw Patrol",
                position = 1,
                subCategoryId = "",
            ),
            Product(
                categoryId = "1",
                description = "description",
                details = emptyList(),
                id = "2",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
                name = "Paw Patrol",
                position = 1,
                subCategoryId = "",
            ),
            Product(
                categoryId = "1",
                description = "description",
                details = emptyList(),
                id = "3",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Fdesayunos_300x300.webp?alt=media&token=db13db47-455d-452f-ab33-2c3f3c80b50e",
                name = "Paw Patrol",
                position = 1,
                subCategoryId = "",
            ),
            Product(
                categoryId = "1",
                description = "description",
                details = emptyList(),
                id = "4",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FProducts%2Fpaw_patrol_300x300.webp?alt=media&token=3b8b5b1a-5b0a-4b0a-9b0a-5b0a4b0a9b0a",
                name = "Paw Patrol",
                position = 1,
                subCategoryId = "",
            ),
            Product(
                categoryId = "1",
                description = "description",
                details = emptyList(),
                id = "5",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FProducts%2Fpaw_patrol_300x300.webp?alt=media&token=3b8b5b1a-5b0a-4b0a-9b0a-5b0a4b0a9b0a",
                name = "Paw Patrol",
                position = 1,
                subCategoryId = "",
            ),
            Product(
                categoryId = "1",
                description = "description",
                details = emptyList(),
                id = "6",
                image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FProducts%2Fpaw_patrol_300x300.webp?alt=media&token=3b8b5b1a-5b0a-4b0a-9b0a-5b0a4b0a9b0a",
                name = "Paw Patrol",
                position = 1,
                subCategoryId = "",
            )
        ),
        statusCode = 200,
        totalPages = 1
    )
    val listCategories = listOf(
        Category(
            id = "1",
            image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
            name = "Paw Patrol",
            position = 0
        ),
        Category(
            id = "2",
            image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
            name = "Paw Patrol 2",
            position = 1
        ),
        Category(
            id = "3",
            image = "https://firebasestorage.googleapis.com/v0/b/el-rosal-177df.appspot.com/o/RosalStorage%2FCategories%2Finfantil_categoria_300x300.webp?alt=media&token=4d313bf4-88cf-4f7a-bdcb-6a0cad7cc2c6",
            name = "Paw Patrol 3",
            position = 2
        )
    )
    val subCategories = SubCategories("Infantil", listCategories)
    ElRosalTheme {
        ProductsScreen(
            subCategories = subCategories,
            products = listProducts,
            id = "1",
        )
    }
}
