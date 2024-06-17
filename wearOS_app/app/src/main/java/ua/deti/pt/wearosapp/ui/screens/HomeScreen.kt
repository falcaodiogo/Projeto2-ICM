package ua.deti.pt.wearosapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import kotlinx.coroutines.delay
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.ui.components.CircularProgressBar
import ua.deti.pt.wearosapp.ui.components.progressFlow
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.tooling.preview.Preview


@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun HomeScreen(
    controller: NavController
) {
    var showButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        showButton = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.violet_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .padding(24.dp)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    this@Column.AnimatedVisibility(
                        visible = showButton,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Button(
                            modifier = Modifier.width(130.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFFe54f7f)
                            ),
                            onClick = {
                                controller.navigate("PreparingExercise")
                            }
                        ) {
                            Text(text = "Start Moving!")
                        }
                    }
                    val progressFlow = remember { progressFlow(delayTime = 15L) }
                    CircularProgressBar(
                        progress = 1f,
                        startAngle = 180f,
                        initialSize = 120.dp,
                        expandedSize = 980.dp,
                        strokeWidth = 16.dp,
                        progressArcColor1 = Color(0xFFdcfb78),
                        backgroundArcColor = Color(0xFFe54f7f),
                        animationOn = true
                    )
                }
            }
        }
    }
}
