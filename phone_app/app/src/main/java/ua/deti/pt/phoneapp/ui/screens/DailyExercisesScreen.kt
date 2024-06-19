package ua.deti.pt.phoneapp.ui.screens

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.data.health.DailyExerciseViewModel
import ua.deti.pt.phoneapp.data.health.DailyExercisesUiState
import ua.deti.pt.phoneapp.data.notifications.NotificationHandler
import ua.deti.pt.phoneapp.ui.components.dialog.AddExerciseDialog
import ua.deti.pt.phoneapp.ui.components.dialog.AlertDialog
import ua.deti.pt.phoneapp.ui.components.segments.Segments

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DailyExercisesScreen(
    day: String? = "",
    viewModel: DailyExerciseViewModel,
    context: Context
) {
    val uiState by viewModel.uiState.collectAsState()
    val showAddExerciseDialog by viewModel.showAddExerciseDialog.collectAsState()
    val showUpdateStateDialog by viewModel.showUpdateStateDialog.collectAsState()
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.exercises_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        if (showAddExerciseDialog) {
            AddExerciseDialog(
                day = day ?: "",
                modifier = Modifier.padding(16.dp),
                onDismissRequest = { viewModel.dismissAddExerciseDialog() },
                onSaveExercise = { exerciseType, day ->
                    viewModel.upsertExercise(exerciseType, day)
                }
            )
        }
        if (showUpdateStateDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissUpdateStateDialog() },
                onConfirmation = { viewModel.dismissUpdateStateDialog() },
                dialogTitle = "Exercise Information",
                dialogContent = {
                    Text(text = "Exercise started with success. Change to the smartwatch to complete the exercise.")
                },
                icon = Icons.Default.Info
            )
        }
        when (uiState) {
            is DailyExercisesUiState.Loading -> {
                CircularProgressIndicator()
            }
            is DailyExercisesUiState.Success -> {
                val exercises = (uiState as DailyExercisesUiState.Success).exercises

                if (exercises.isNotEmpty() && exercises.all { it.dayOfWeek == day && it.exerciseStage == 2 }) {
                    notificationHandler.showSimpleNotification()
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 90.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                            Text(
                                text = "$day",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                            )

                            FloatingActionButton(
                                onClick = {
                                    viewModel.showAddExerciseDialog()
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Exercise"
                                )
                            }
                        }

                    }
                    item {
                        if (exercises.isNotEmpty() && exercises.any { it.dayOfWeek == day }) {
                            Column (
                                modifier = Modifier
                                    .height(600.dp)
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                exercises.forEach { exercise ->
                                    if (exercise.dayOfWeek == day) {
                                        Segments(
                                            onClick = {
                                                when (exercise.exerciseStage) {
                                                    0 -> {
                                                        viewModel.updateExerciseState(exercise, 1)
                                                        viewModel.showUpdateStateDialog()
                                                    }

                                                    1 -> {
                                                        viewModel.updateExerciseState(exercise, 2)
                                                    }
                                                }
                                            },
                                            title = exercise.exerciseType,
                                            description = "",
                                            color = Color(0xFF657CF5),
                                            exerciseStage = exercise.exerciseStage
                                        )
                                    }
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.height(256.dp))
                            Text(
                                text = "No exercises for today add some!",
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                fontSize = 24.sp,
                            )
                        }
                    }

                }
            }
            is DailyExercisesUiState.Error -> {
                Text(
                    text = (uiState as DailyExercisesUiState.Error).message,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
