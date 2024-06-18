package ua.deti.pt.phoneapp.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ua.deti.pt.phoneapp.ui.events.DailyExerciseState
import ua.deti.pt.phoneapp.ui.events.DailyExercisesEvent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.unit.dp

@Composable
fun AddExerciseDialog(
    state: DailyExerciseState,
    onEvent: (DailyExercisesEvent) -> Unit,
    day: String,
    modifier: Modifier = Modifier
) {
    onEvent(DailyExercisesEvent.SetDayOfWeek(day))
    AlertDialog(
        modifier = modifier,
        title = {
            Text(text = "Add Exercise")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.exerciseType,
                    onValueChange = { onEvent(DailyExercisesEvent.SetExerciseType(it)) },
                    placeholder = { Text("Exercise Type") }
                )
            }
        },
        onDismissRequest = {
            onEvent(DailyExercisesEvent.HideDialog)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onEvent(DailyExercisesEvent.SaveDailyExercise)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(DailyExercisesEvent.HideDialog)
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}