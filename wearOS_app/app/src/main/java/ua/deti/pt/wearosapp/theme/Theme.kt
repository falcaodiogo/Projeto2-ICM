package ua.deti.pt.wearosapp.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme


@Composable
fun WearOSAppTheme(
    content: @Composable () -> Unit
) {
    /**
     * Empty theme to customize for your app.
     * See: https://developer.android.com/jetpack/compose/designsystems/custom
     */
    MaterialTheme(
        colors = wearColorPalette,
        typography = Typography,
        content = content
    )
}