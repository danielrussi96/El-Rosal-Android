package com.app.elrosal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.app.elrosal.R


@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String,
    enableCrossFade: Boolean = true,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .placeholder(R.drawable.rosal_escudo)
            .error(R.drawable.rosal_escudo)
            .crossfade(enableCrossFade)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )

}