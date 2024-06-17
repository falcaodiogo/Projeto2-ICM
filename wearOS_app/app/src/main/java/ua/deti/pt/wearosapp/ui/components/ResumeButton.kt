@file:OptIn(ExperimentalHorologistApi::class)

package ua.deti.pt.wearosapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.material.Button
import ua.deti.pt.wearosapp.R

@Composable
fun ResumeButton(onResumeClick: () -> Unit) {
    Button(
        imageVector = Icons.Default.PlayArrow,
        contentDescription = stringResource(id = R.string.resume_button_cd),
        onClick = onResumeClick
    )
}

@Preview
@Composable
fun ResumeButtonPreview() {
    ResumeButton { }
}