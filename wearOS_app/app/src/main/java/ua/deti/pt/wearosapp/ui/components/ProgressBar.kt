package ua.deti.pt.wearosapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import com.google.android.horologist.compose.ambient.AmbientState

@Composable
fun ProgressBar(
    ambientState: AmbientState,
    modifier: Modifier = Modifier
) {
    when (ambientState) {
        is AmbientState.Interactive -> {
            CircularProgressIndicator(
                indicatorColor = MaterialTheme.colors.secondary,
                trackColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                strokeWidth = 4.dp,
                modifier = modifier
            )
        }

        is AmbientState.Ambient -> {
            CircularProgressIndicator(
                indicatorColor = MaterialTheme.colors.secondary,
                trackColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                strokeWidth = 4.dp,
                modifier = modifier,
                progress = 0f
            )
        }
    }
}

@Preview
@Composable
fun ProgressBarPreview() {
    ProgressBar(
        AmbientState.Interactive,
        Modifier
            .size(100.dp)
            .background(Color.Black)
    )
}

@Preview
@Composable
fun ProgressBarPreviewAmbient() {
    ProgressBar(
        AmbientState.Ambient(),
        Modifier
            .size(100.dp)
            .background(Color.Black)
    )
}