package ua.deti.pt.phoneapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.ui.components.sleepchart.SingleLineChartWithGridLines

@Composable
fun SleepScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sleep_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        val pointsData = listOf(
            Point(0f, 40f), Point(1f, 90f), Point(2f, 0f), Point(3f, 60f), Point(4f, 10f))
        
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Your sleep", color = Color.White,
                fontSize = 32.sp, modifier = Modifier.padding(bottom = 40.dp))
            Spacer(modifier = Modifier.padding(10.dp))
            Box (modifier = Modifier.padding(24.dp).clip(shape = RoundedCornerShape(16.dp)) ) {
                SingleLineChartWithGridLines(pointsData = pointsData)
            }
        }
    }
}