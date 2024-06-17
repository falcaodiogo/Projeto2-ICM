package ua.deti.pt.wearosapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text

@Composable
fun DistanceText(distance: Double?) {
    Text(text = formatDistanceKm(distance))
}

@Preview
@Composable
fun DistanceTextPreview() {
    DistanceText(distance = 214.0)
}