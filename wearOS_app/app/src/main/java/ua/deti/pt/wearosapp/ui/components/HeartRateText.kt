package ua.deti.pt.wearosapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text

@Composable
fun HeartRateText(hr: Double?) {
    Text(text = "${hr ?: "--"}")
}

@Preview
@Composable
fun HeartRateTextPreview() {
    HeartRateText(hr = 291.9)
}

@Preview
@Composable
fun HeartRateTextPreviewMissing() {
    HeartRateText(hr = null)
}