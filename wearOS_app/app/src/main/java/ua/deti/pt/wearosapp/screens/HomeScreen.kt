package ua.deti.pt.wearosapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.components.CircularProgressBar
import ua.deti.pt.wearosapp.components.progressFlow

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image (
            painter = painterResource(id = R.drawable.violet_background),
            contentDescription = "Home Screen Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        LazyColumn (
            modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            item {
                Text(text = "Health", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                Box(modifier = Modifier.padding(vertical = 10.dp))
            }

            item {
                val progressFlow = remember { progressFlow(delayTime = 10L) }
                CircularProgressBar(
                    progress = 0.76f,
                    startAngle = 130f,
                    size = 96.dp,
                    strokeWidth = 14.dp,
                    progressArcColor1 = Color(0xFFdcfb78),
                    backgroundArcColor = Color(0xFFe54f7f),
                    animationOn = true
                )
                Box(modifier = Modifier.padding(vertical = 10.dp))
            }

            item {
                Text(text = "1,073 Cal", fontSize = 16.sp)
                Text(text = "3.2 km", fontSize = 16.sp)
            }

        }
    }
}