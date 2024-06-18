package ua.deti.pt.phoneapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.data.health.DailyExerciseViewModel
import ua.deti.pt.phoneapp.data.health.DailyExercisesUiState
import ua.deti.pt.phoneapp.ui.components.dialog.AddExerciseDialog
import ua.deti.pt.phoneapp.ui.components.segments.Segments

@Composable
fun DailyExercisesScreen(
    day: String? = "",
    viewModel: DailyExerciseViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val showAddExerciseDialog by viewModel.showAddExerciseDialog.collectAsState()

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
        when (uiState) {
            is DailyExercisesUiState.Loading -> {
                CircularProgressIndicator()
            }
            is DailyExercisesUiState.Success -> {
                val exercises = (uiState as DailyExercisesUiState.Success).exercises
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 90.dp, start = 16.dp, end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "$day Exercises",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    }
                    items(exercises) { exercise ->
                        if (exercise.dayOfWeek == day) {
                            Segments(
                                onClick = { /*TODO*/ },
                                title = exercise.exerciseType,
                                description = "",
                                color = Color(0xFF657CF5),
                                exerciseStage = exercise.exerciseStage
                            )
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
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