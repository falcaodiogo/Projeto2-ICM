package ua.deti.pt.phoneapp.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun AddExerciseDialog(
    day: String,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSaveExercise: (String, String) -> Unit
) {
    var exerciseType by remember { mutableStateOf("") }

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
                    value = exerciseType,
                    onValueChange = { exerciseType = it },
                    placeholder = { Text("Exercise Type") }
                )
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    onSaveExercise(exerciseType, day)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Dismiss")
            }
        }
    )
}