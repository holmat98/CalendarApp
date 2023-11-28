package com.mateuszholik.uicomponents.animations

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CommonAnimation(
    @RawRes animationResId: Int,
    modifier: Modifier = Modifier,
    isLooped: Boolean = false,
) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(animationResId))

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = if (isLooped) {
            LottieConstants.IterateForever
        } else {
            1
        },
    )
}
