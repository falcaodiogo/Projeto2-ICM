package ua.deti.pt.phoneapp.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.Auth.UserData
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.database.daos.UserDao
import ua.deti.pt.phoneapp.database.entities.User
import ua.deti.pt.phoneapp.ui.components.dialog.AlertDialog
import ua.deti.pt.phoneapp.ui.components.segments.Segments

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    context: Context,
    googleAuthUiClient: GoogleAuthUiClient
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    var newStepsGoal by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()


    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
                if (newStepsGoal.isNotEmpty()) {
                    coroutineScope.launch {
                        updateStepsGoal(googleAuthUiClient, userData, newStepsGoal.toInt(), context)
                    }
                } else {
                    Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            },
            dialogTitle = "Change your daily steps goal here.",
            dialogContent = { AddStepsGoalScreen(
                newStepsGoalArg = newStepsGoal,
                onNewStepsGoalChange = {
                    newStepsGoal = it
                }
            ) },
            icon = Icons.Default.Brush
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.settings_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 65.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = "Settings",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.size(32.dp))
            Segments(
                onClick = {
                    openAlertDialog.value = true
                    println("Clicked on the first segment")
                },
                title = "Goals",
                description = "",
                color = Color(0xff5EBFBF),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Segments(
                onClick = onSignOut,
                title = "Logout",
                description = "",
                color = Color(0xff5EBFBF),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Segments(
                onClick = {},
                title = "Others",
                description = "",
                color = Color(0xff5EBFBF),
            )
            if (userData?.profilePictureUrl != null && userData.username != null) {
                Row(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 230.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = userData.username,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

suspend fun updateStepsGoal(
    googleAuthUiClient: GoogleAuthUiClient,
    userData: UserData?,
    newStepsGoal: Int,
    context: Context
) {
    userData?.let {
        it.username?.let { it1 -> googleAuthUiClient.updateUserStepsGoal(it1, newStepsGoal) }
        Toast.makeText(context, "Steps goal updated", Toast.LENGTH_SHORT).show()
    } ?: run {
        Toast.makeText(context, "User data is missing", Toast.LENGTH_SHORT).show()
    }
}


@Composable
fun AddStepsGoalScreen(
    newStepsGoalArg: String,
    onNewStepsGoalChange: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "New steps goal:",
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = newStepsGoalArg,
            onValueChange = { onNewStepsGoalChange(it) },
            placeholder = { Text(text = "2000") },
        )
    }
}
