@file:OptIn(ExperimentalHorologistApi::class)

package ua.deti.pt.wearosapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.ambient.AmbientState
import ua.deti.pt.wearosapp.repositories.ServiceState
import ua.deti.pt.wearosapp.service.ExerciseServiceState
import ua.deti.pt.wearosapp.theme.WearOSAppTheme
import ua.deti.pt.wearosapp.ui.state.ExerciseScreenState
import ua.deti.pt.wearosapp.ui.state.SummaryScreenState

@Composable
fun ExerciseRoute(
    ambientState: AmbientState,
    modifier: Modifier = Modifier,
    onSummary: (SummaryScreenState) -> Unit,
    onRestart: () -> Unit,
    onFinishActivity: () -> Unit
) {

}

// Shows an error that occurred when starting an exercise
@Composable
fun ErrorStartingExerciseScreen(
    onRestart: () -> Unit,
    onFinishActivity: () -> Unit,
    uiState: ExerciseScreenState
) {

}

// Shows while an exercise is in progress
@Composable
fun ExerciseScreen(
    onPauseClick: () -> Unit,
    onEndClick: () -> Unit,
    onResumeClick: () -> Unit,
    onStartClick: () -> Unit,
    uiState: ExerciseScreenState,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun ExerciseControlButtons(
    uiState: ExerciseScreenState,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit,
    onResumeClick: () -> Unit,
    onPauseClick: () -> Unit
) {

}

@Composable
private fun DistanceAndLapsRow(uiState: ExerciseScreenState) {

}

@Composable
private fun HeartRateAndCaloriesRow(uiState: ExerciseScreenState) {

}

@Composable
private fun DurationRow(uiState: ExerciseScreenState) {

}

@WearPreviewDevices
@Composable
fun ExerciseScreenPreview() {
    WearOSAppTheme {
        ExerciseScreen(
            onPauseClick = { },
            onEndClick = { },
            onResumeClick = { },
            onStartClick = { },
            uiState = ExerciseScreenState(
                hasExerciseCapabilities = true,
                isTrackingAnotherExercise = false,
                serviceState = ServiceState.Connected(
                    ExerciseServiceState()
                ),
                exerciseState = ExerciseServiceState()
            )
        )
    }
}

@WearPreviewDevices
@Composable
fun ErrorStartingExerciseScreenPreview() {
    WearOSAppTheme {
        ErrorStartingExerciseScreen(
            onRestart = { },
            onFinishActivity = { },
            uiState = ExerciseScreenState(
                hasExerciseCapabilities = true,
                isTrackingAnotherExercise = false,
                serviceState = ServiceState.Connected(
                    ExerciseServiceState()
                ),
                exerciseState = ExerciseServiceState()
            )
        )
    }
}