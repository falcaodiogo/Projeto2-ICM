package ua.deti.pt.wearosapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import ua.deti.pt.wearosapp.R

@Composable
fun AcquiredCheck() {
    Image(
        Icons.Default.CheckCircleOutline,
        contentDescription = stringResource(id = R.string.GPS_acquired),
        colorFilter = tint(MaterialTheme.colors.secondary),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}

@Preview
@Composable
fun AcquiredCheckPreview() {
    AcquiredCheck()
}