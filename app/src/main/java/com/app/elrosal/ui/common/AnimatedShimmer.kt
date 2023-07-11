package com.app.elrosal.ui.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.app.elrosal.utils.ConstantsViews

@Composable
fun AnimatedShimmer(brushValue: @Composable (Brush) -> Unit) {
    colorScheme.secondary.apply {
        val shimmerColor = listOf(
            copy(alpha = ConstantsViews.ALPHA_06F),
            copy(alpha = ConstantsViews.ALPHA_02F),
            copy(alpha = ConstantsViews.ALPHA_06F)
        )
        val transition = rememberInfiniteTransition(label = ConstantsViews.SHIMMER_TRANSITION)
        val translateAnim = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = ConstantsViews.TRANSLATE_ANIM
        )

        val brush = Brush.linearGradient(
            colors = shimmerColor,
            start = Offset.Zero,
            end = Offset(x = translateAnim.value, y = translateAnim.value)
        )
        brushValue(brush)
    }
}