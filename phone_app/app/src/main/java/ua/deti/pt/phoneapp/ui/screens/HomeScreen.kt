package ua.deti.pt.phoneapp.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ua.deti.pt.phoneapp.Auth.GoogleAuthUiClient
import ua.deti.pt.phoneapp.Auth.UserData
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.ui.components.circularbar.CircularProgressBar
import ua.deti.pt.phoneapp.ui.components.circularbar.progressFlow
import ua.deti.pt.phoneapp.ui.components.map.MapScreen


@Composable
fun HomeScreen(
    googleAuthUiClient: GoogleAuthUiClient,
    userData: UserData
) {
    var steps by remember { mutableStateOf(0) }
    var calories by remember { mutableStateOf(0f) }
    var defined_user_steps_goal = 2000f
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            defined_user_steps_goal =
                userData.username?.let { googleAuthUiClient.getUserStepsGoal(it).toFloat() }!!
            println("Defined user calories goal: $defined_user_steps_goal")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.mainscreen_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(modifier = Modifier
            .padding(24.dp)
            .padding(top = 70.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            HealthConnectScreen(onStepsAndCaloriesUpdated = { updatedSteps, updatedCalories ->
                steps = updatedSteps
                println("Updated steps: $steps")
                calories = updatedCalories
            }, onSleepDataUpdated = {
            })
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Box (
                    contentAlignment = Alignment.Center,
                ) {
                    val progressFlow = remember { progressFlow(delayTime = 10L) }
                    val progressState = progressFlow.collectAsState(initial = 0f)
                    val progress = 1 - (defined_user_steps_goal/steps)
                    println("Progress: $progress")
                    CircularProgressBar(
                        progress = progress,
                        startAngle = 180f,
                        size = 160.dp,
                        strokeWidth = 24.dp,
                        progressArcColor1 = Color(0xFFdcfb78),
                        backgroundArcColor = Color(0xFFe54f7f),
                        animationOn = true
                    )
                }
                Column {
                    Box {
                        Text(text = String.format("%.01f", calories), fontSize = 40.sp)
                        Text(text = "cal", fontSize = 20.sp, modifier = Modifier.padding(top = 48.dp))
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Box {
                        Text(text = String.format("%.1f", steps * 0.000762f), fontSize = 40.sp)
                        Text(text = "km", fontSize = 20.sp, modifier = Modifier.padding(top = 48.dp))
                    }
                }

            }
            Box(modifier = Modifier
                .height(524.dp)
                .padding(top = 60.dp)
                .clip(shape = RoundedCornerShape(16.dp))) {
                MapScreen()
            }
        }
    }
}
