package com.app.elrosal.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.app.elrosal.R


@Composable
fun AsyncImagePainter(
    url: String,
    content: @Composable (AsyncImagePainter) -> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .placeholder(R.drawable.rosal_escudo)
            .error(R.drawable.rosal_escudo)
            .build()
    )
    content(painter)
}