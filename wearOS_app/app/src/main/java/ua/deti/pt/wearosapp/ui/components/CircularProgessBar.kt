package ua.deti.pt.wearosapp.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@Composable
fun CircularProgressBar(
    progress: Float = 0f,
    startAngle: Float = 270f,
    initialSize: Dp = 96.dp,
    expandedSize: Dp = 120.dp,
    strokeWidth: Dp = 12.dp,
    backgroundArcColor: Color = Color.LightGray,
    progressArcColor1: Color = Color.Blue,
    progressArcColor2: Color = progressArcColor1,
    animationOn: Boolean = false,
    animationDuration: Int = 1000
) {
    val currentProgress = remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress.floatValue,
        animationSpec = if (animationOn) tween(animationDuration) else tween(0),
        label = "Progress Animation"
    )

    val currentSize = remember { mutableStateOf(initialSize) }
    val animatedSize by animateDpAsState(
        targetValue = if (currentProgress.floatValue >= progress) expandedSize else initialSize,
        animationSpec = tween(animationDuration),
        label = "Size Animation"
    )

    LaunchedEffect(animationOn, progress) {
        if (animationOn) {
            progressFlow(progress).collect { value ->
                currentProgress.floatValue = value
                if (value >= progress) {
                    currentSize.value = expandedSize
                }
            }
        } else {
            currentProgress.floatValue = progress
            currentSize.value = expandedSize
        }
    }

    Canvas(modifier = Modifier.size(animatedSize)) {
        val strokeWidthPx = strokeWidth.toPx()
        val arcSize = animatedSize.toPx() - strokeWidthPx

        drawArc(
            color = backgroundArcColor,
            startAngle = startAngle,
            sweepAngle = 360f,
            useCenter = false,
            size = Size(animatedSize.toPx(), animatedSize.toPx()),
            style = Stroke(width = strokeWidthPx)
        )

        val gradientBrush = Brush.verticalGradient(
            colors = listOf(progressArcColor1, progressArcColor2, progressArcColor1)
        )

        withTransform({
            rotate(degrees = startAngle, pivot = center)
        }) {
            drawArc(
                brush = gradientBrush,
                startAngle = 0f,
                sweepAngle = animatedProgress * 360,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

fun progressFlow(
    targetProgress: Float = 1f,
    step: Float = 0.01f,
    delayTime: Long = 1L
): Flow<Float> {
    return flow {
        var progress = 0f
        while (progress <= targetProgress) {
            progress += step
            emit(progress)
            delay(delayTime)
        }
    }
}
