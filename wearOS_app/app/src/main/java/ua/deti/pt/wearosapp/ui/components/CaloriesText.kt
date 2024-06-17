package ua.deti.pt.wearosapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text

@Composable
fun CaloriesText(calories: Double?) {
    if (calories != null)
        Text(text = formatCalories(calories))
    else
        Text(text = "--")
}

@Preview
@Composable
fun CaloriesTextPreview() {
    CaloriesText(calories = 75.0)
}