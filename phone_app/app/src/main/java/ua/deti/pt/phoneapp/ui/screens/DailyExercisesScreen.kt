package ua.deti.pt.phoneapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.deti.pt.phoneapp.Auth.UserData
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.ui.components.dialog.AddExerciseDialog
import ua.deti.pt.phoneapp.ui.components.segments.Segments
import ua.deti.pt.phoneapp.ui.events.DailyExerciseState
import ua.deti.pt.phoneapp.ui.events.DailyExercisesEvent

@Composable
fun DailyExercisesScreen(
    day: String? = "",
    state: DailyExerciseState,
    onEvent: (DailyExercisesEvent) -> Unit
) {
    Log.d("DailyExercisesScreen", "day: $day")
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(DailyExercisesEvent.ShowDialog)
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Exercise")
            }
        },
    ) { padding ->

        if (state.isAddingNewExercise) {
            AddExerciseDialog(state = state, onEvent = onEvent, day = day ?: "")
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.exercises_background),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
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

                items(state.exercises) { exercise ->
                    Segments(
                        onClick = { /*TODO*/ },
                        title = exercise.exerciseType,
                        description = "",
                        color = Color(0xFF657CF5),
                        hasChevron = true
                    )
                }
            }
        }
    }
}