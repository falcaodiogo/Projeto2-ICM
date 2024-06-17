@file:OptIn(ExperimentalHorologistApi::class)

package ua.deti.pt.wearosapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.automirrored.filled._360
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.ambient.AmbientState
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.AlertDialog
import com.google.android.horologist.health.composables.ActiveDurationText
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.repositories.ServiceState
import ua.deti.pt.wearosapp.service.ExerciseServiceState
import ua.deti.pt.wearosapp.theme.WearOSAppTheme
import ua.deti.pt.wearosapp.ui.components.CaloriesText
import ua.deti.pt.wearosapp.ui.components.DistanceText
import ua.deti.pt.wearosapp.ui.components.HeartRateText
import ua.deti.pt.wearosapp.ui.components.PauseButton
import ua.deti.pt.wearosapp.ui.components.ResumeButton
import ua.deti.pt.wearosapp.ui.components.StartButton
import ua.deti.pt.wearosapp.ui.components.StopButton
import ua.deti.pt.wearosapp.ui.components.formatElapsedTime
import ua.deti.pt.wearosapp.ui.state.ExerciseScreenState
import ua.deti.pt.wearosapp.ui.state.SummaryScreenState
import ua.deti.pt.wearosapp.ui.viewModels.ExerciseViewModel

@Composable
fun ExerciseRoute(
    ambientState: AmbientState,
    onSummary: (SummaryScreenState) -> Unit,
    onRestart: () -> Unit,
    onFinishActivity: () -> Unit
) {

    val viewModel = hiltViewModel<ExerciseViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isEnded) {
        SideEffect {
            onSummary(uiState.toSummary())
        }
    }

    if (uiState.error != null) {
        ErrorStartingExerciseScreen(
            onRestart = onRestart,
            onFinishActivity = onFinishActivity,
            uiState = uiState
        )
    } else if (ambientState is AmbientState.Interactive) {
        ExerciseScreen(
            onPauseClick = { viewModel.pauseExercise() },
            onEndClick = { viewModel.endExercise() },
            onResumeClick = { viewModel.resumeExercise() },
            onStartClick = { viewModel.startExercise() },
            uiState = uiState
        )
    }
}

// Shows an error that occurred when starting an exercise
@Composable
fun ErrorStartingExerciseScreen(
    onRestart: () -> Unit,
    onFinishActivity: () -> Unit,
    uiState: ExerciseScreenState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.violet_background),
            contentDescription = "Exercise Error Screen",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        AlertDialog(
            showDialog = true,
            title = stringResource(id = R.string.error_starting_exercise),
            message = "${uiState.error ?: stringResource(id = R.string.unknown_error)}. ${
                stringResource(id = R.string.try_again)
            }",
            onCancel = onFinishActivity,
            onOk = onRestart,
        )
    }
}

// Shows while an exercise is in progress
@Composable
fun ExerciseScreen(
    onPauseClick: () -> Unit,
    onEndClick: () -> Unit,
    onResumeClick: () -> Unit,
    onStartClick: () -> Unit,
    uiState: ExerciseScreenState
) {
    val columnState = rememberResponsiveColumnState(
        contentPadding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.Chip
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.violet_background),
            contentDescription = "Exercise Screen Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        ScreenScaffold(scrollState = columnState) {
            ScalingLazyColumn(
                columnState = columnState,
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    DurationRow(uiState = uiState)
                }

                item {
                    HeartRateAndCaloriesRow(uiState = uiState)
                }

                item {
                    DistanceAndLapsRow(uiState = uiState)
                }

                item {
                    ExerciseControlButtons(
                        uiState = uiState,
                        onStartClick = onStartClick,
                        onEndClick = onEndClick,
                        onResumeClick = onResumeClick,
                        onPauseClick = onPauseClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseControlButtons(
    uiState: ExerciseScreenState,
    onStartClick: () -> Unit,
    onEndClick: () -> Unit,
    onResumeClick: () -> Unit,
    onPauseClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        if (uiState.isEnding) {
            StartButton(onStartClick)
        } else {
            StopButton(onEndClick)
        }

        if (uiState.isPaused) {
            ResumeButton(onResumeClick)
        } else {
            PauseButton(onPauseClick)
        }
    }
}

@Composable
private fun DistanceAndLapsRow(uiState: ExerciseScreenState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row {
            Icon(
                imageVector = Icons.AutoMirrored.Default.TrendingUp,
                contentDescription = stringResource(id = R.string.distance)
            )
            DistanceText(distance = uiState.exerciseState?.exerciseMetrics?.distance)
        }

        Row {
            Icon(
                imageVector = Icons.AutoMirrored.Default._360,
                contentDescription = stringResource(id = R.string.laps)
            )
            Text(text = uiState.exerciseState?.exerciseLaps?.toString() ?: "--")
        }
    }
}

@Composable
private fun HeartRateAndCaloriesRow(uiState: ExerciseScreenState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = stringResource(id = R.string.heart_rate)
            )
            HeartRateText(hr = uiState.exerciseState?.exerciseMetrics?.heartRate)
        }
        Row {
            Icon(
                imageVector = Icons.Default.LocalFireDepartment,
                contentDescription = stringResource(id = R.string.calories)
            )
            CaloriesText(calories = uiState.exerciseState?.exerciseMetrics?.calories)
        }
    }
}

@Composable
private fun DurationRow(uiState: ExerciseScreenState) {
    val lastActiveDurationCheckpoint = uiState.exerciseState?.activeDurationCheckpoint
    val exerciseState = uiState.exerciseState?.exerciseState

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.WatchLater,
                contentDescription = stringResource(id = R.string.duration)
            )
            if (exerciseState != null && lastActiveDurationCheckpoint != null) {
                ActiveDurationText(
                    checkpoint = lastActiveDurationCheckpoint,
                    state = uiState.exerciseState.exerciseState
                ) {
                    Text(text = formatElapsedTime(elapsedDuration = it, includeSeconds = true))
                }
            } else {
                Text(text = "--")
            }
        }
    }
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