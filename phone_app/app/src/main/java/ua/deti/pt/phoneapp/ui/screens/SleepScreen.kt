package ua.deti.pt.phoneapp.ui.screens

import android.health.connect.datatypes.SleepSessionRecord
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import ua.deti.pt.phoneapp.ui.components.sleepchart.SingleLineChartWithGridLines
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SleepScreen(sleepSessions: List<SleepSessionRecord>) {
    val pointsData = sleepSessions.mapIndexed { index, session ->
        val durationMinutes = ChronoUnit.MINUTES.between(session.startTime, session.endTime)
        Point(index.toFloat(), durationMinutes.toFloat())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your sleep",
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 40.dp)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Box(modifier = Modifier.padding(20.dp)) {
            if (pointsData.isNotEmpty()) {
                Row {
                    Text(modifier = Modifier.padding(top = 70.dp, start = 12.dp), text = "oi")
                    Box(
                        modifier = Modifier
                            .padding(24.dp)
                            .padding(start = 8.dp)
                    ) {
                        SingleLineChartWithGridLines(pointsData = pointsData)
                    }
                }
            } else {
                Text(text = "No sleep data available.", color = Color.White)
            }
        }
    }
}
