package com.app.elrosal.ui.common

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun TitleContent(modifier: Modifier = Modifier, title: String) {
    Text(
        modifier = modifier,
        style = typography.headlineLarge,
        text = title,
        color = colorScheme.tertiary,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun SubTitleContent(modifier: Modifier = Modifier, subTitle: String) {
    Text(
        modifier = modifier,
        text = subTitle,
        style = typography.headlineSmall,
        color = colorScheme.tertiary
    )
}


@Composable
fun TitleProducts(titleProducts: String) {
    Text(
        text = titleProducts,
        color = colorScheme.secondary,
        style = typography.headlineSmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun DescriptionProducts(modifier: Modifier, descriptionProducts: String, maxLines: Int = 3) {
    Text(
        modifier = modifier,
        text = descriptionProducts,
        maxLines = maxLines,
        style = typography.bodyMedium,
        textAlign = TextAlign.Justify,
        color = colorScheme.tertiary,
        overflow = TextOverflow.Ellipsis,
    )
}