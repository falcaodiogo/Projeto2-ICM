package ua.deti.pt.phoneapp.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.health.connect.client.records.SleepSessionRecord
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphFillType
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphVisibility
import ua.deti.pt.phoneapp.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SleepScreen(
    context: Context
) {
    val sessionRecords = rememberSaveable { mutableStateOf<List<SleepSessionRecord.Stage>>(emptyList()) }
    val pointsData = rememberSaveable { mutableStateOf<List<LineData>>(emptyList()) }
    var dataProcessed by remember { mutableStateOf(false) }
    var startTimeSleep = remember { Instant.now() }
    var endTimeSleep = remember { Instant.now() }
    val stageMap = mapOf(
        2f to "Sleeping",
        1f to "Awake",
        7f to "Awake in bed",
        5f to "Deep sleep",
        4f to "Light sleep",
        3f to "Out of bed",
        6f to "REM"
    )

    HealthConnectScreen(onStepsAndCaloriesUpdated = { steps, calories ->
    }, onSleepDataUpdated = { sleepSessionRecords ->
        sessionRecords.value = sleepSessionRecords
        dataProcessed = false
    })

    if (!dataProcessed && sessionRecords.value.isNotEmpty()) {
        val newPointsData = mutableListOf<LineData>()
        val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
        for (record in sessionRecords.value) {
            val formattedTime = formatter.format(record.startTime)
            val value = when (record.stage) {
                SleepSessionRecord.STAGE_TYPE_SLEEPING -> 2f
                SleepSessionRecord.STAGE_TYPE_AWAKE -> 0f
                SleepSessionRecord.STAGE_TYPE_AWAKE_IN_BED -> 7f
                SleepSessionRecord.STAGE_TYPE_DEEP -> 5f
                SleepSessionRecord.STAGE_TYPE_LIGHT -> 1f
                SleepSessionRecord.STAGE_TYPE_OUT_OF_BED -> 3f
                SleepSessionRecord.STAGE_TYPE_REM -> 2f
                else -> 0f
            }
            val lineData = LineData(formattedTime, value)
            newPointsData.add(lineData)
        }
        pointsData.value = newPointsData
        dataProcessed = true
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sleep_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your sleep",
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 90.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Box(modifier = Modifier.padding(start = 20.dp)) {
                if (sessionRecords.value.isNotEmpty()) {
                    Row {
                        Column {
                            Text(
                                modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                                text = "REM"
                            )
                            Text(
                                modifier = Modifier.padding(top = 110.dp, start = 12.dp),
                                text = "Light sleep"
                            )
                            Text(
                                modifier = Modifier.padding(top = 110.dp, start = 12.dp),
                                text = "Awake"
                            )
                        }
                        LineGraph(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            data = pointsData.value,
                            onPointClick = { value: LineData ->
                                showToast(context, value.x, stageMap[value.y] ?: "Awake")
                            },
                            style = LineGraphStyle(
                                visibility = LineGraphVisibility(
                                    isYAxisLabelVisible = false,
                                    isCrossHairVisible = true
                                ),
                                colors = LineGraphColors(
                                    lineColor = Color.White,
                                    pointColor = Color.Black,
                                    clickHighlightColor = Color.White,
                                    fillType = LineGraphFillType.Gradient(
                                        brush = Brush.verticalGradient(
                                            listOf(
                                                Color.Black,
                                                Color.Gray
                                            )
                                        )
                                    )
                                )
                            )
                        )
                    }
                } else {
                    Text(text = "No sleep data available.", color = Color.White)
                }
            }
        }
    }
}

fun showToast(context: Context, time: String, stage: String) {
    Toast.makeText(context, "Time: $time, Stage: $stage", Toast.LENGTH_SHORT).show()
}
