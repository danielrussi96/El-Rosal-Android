package com.app.elrosal.ui.home.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.app.elrosal.MainViewModel
import com.app.elrosal.R
import com.app.elrosal.ui.common.TitleContent
import com.app.elrosal.ui.theme.IMAGE_HEIGHT_CATEGORIES
import com.app.elrosal.ui.theme.LOGO_HEIGHT
import com.app.elrosal.ui.theme.PADDING_16
import com.app.elrosal.utils.ConstantsViews.WEIGHT_1F

@Composable
fun HomeScreen(mainViewModel: MainViewModel, navigateToProductScreen: (String) -> Unit) {
    CategoriesList(mainViewModel = mainViewModel, navigateToProductScreen = navigateToProductScreen)
}

@Composable
fun CategoriesList(mainViewModel: MainViewModel, navigateToProductScreen: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Row(Modifier.padding(PADDING_16)) {
            Image(
                painter = painterResource(id = R.drawable.rosal_escudo),
                contentDescription = stringResource(
                    id = R.string.el_rosal_logo
                ),
                modifier = Modifier
                    .width(LOGO_HEIGHT)
                    .height(LOGO_HEIGHT)
                    .weight(WEIGHT_1F)
            )

            TitleContent(
                modifier = Modifier
                    .weight(WEIGHT_1F)
                    .align(Alignment.CenterVertically),
                title = stringResource(id = R.string.app_name),
            )
        }

        CategoriesContent(mainViewModel = mainViewModel, navigateToProductScreen = navigateToProductScreen)
    }
}