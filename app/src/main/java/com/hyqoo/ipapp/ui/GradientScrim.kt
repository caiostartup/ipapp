package com.hyqoo.ipapp.ui

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import kotlin.math.max

/**
 * Applies a radial gradient scrim in the foreground emanating from the top
 * center quarter of the element.
 */
fun Modifier.radialGradientScrim(color: Color): Modifier {
    val radialGradient = object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val largerDimension = max(size.height, size.width)
            return RadialGradientShader(
                center = size.center.copy(y = size.height / 4),
                colors = listOf(color, Color.Transparent),
                radius = largerDimension / 2,
                colorStops = listOf(0f, 0.9f)
            )
        }
    }
    return this.background(radialGradient)
}