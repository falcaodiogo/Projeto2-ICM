@file:OptIn(ExperimentalHorologistApi::class)

package ua.deti.pt.wearosapp.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.material.Confirmation
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.theme.WearOSAppTheme

/**
 * Screen that appears if an exercise is not available for the particular device
 */
@Composable
fun ExerciseNotAvailable() {
    Confirmation(
        onTimeout = {}
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = stringResource(id = R.string.not_avail),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.not_avail)
            )
        }
    }
}

@WearPreviewDevices
@Composable
fun ExerciseNotAvailablePreview() {
    WearOSAppTheme {
        ExerciseNotAvailable()
    }
}

