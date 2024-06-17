@file:OptIn(ExperimentalHorologistApi::class)

package ua.deti.pt.wearosapp.ui.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.material.AlertDialog
import ua.deti.pt.wearosapp.R

@Composable
fun ExerciseInProgressAlert(
    onNegative: () -> Unit,
    onPositive: () -> Unit,
    showDialog: Boolean
) {
    AlertDialog(
        title = stringResource(id = R.string.exercise_in_progress),
        message = stringResource(id = R.string.ending_continue),
        onCancel = onNegative,
        onOk = onPositive,
        showDialog = showDialog
    )
}

@WearPreviewDevices
@Composable
fun ExerciseInProgressAlertPreview() {
    ExerciseInProgressAlert(onNegative = { }, onPositive = { }, showDialog = true)
}

