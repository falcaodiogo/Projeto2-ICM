package ua.deti.pt.phoneapp.ui.screens

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import java.time.Duration
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SleepScreen(
    context: Context
) {
    val sessionRecords = rememberSaveable { mutableStateOf<List<SleepSessionRecord.Stage>>(emptyList()) }
    val pointsData = rememberSaveable { mutableStateOf<List<LineData>>(emptyList()) }
    var dataProcessed by remember { mutableStateOf(false) }
    var startTimeSleep by remember { mutableStateOf(Instant.now()) }
    var endTimeSleep by remember { mutableStateOf(Instant.now()) }
    var duration by remember { mutableStateOf(Duration.ZERO) }
    val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault())
    val stageMap = mapOf(
        2f to "REM",
        0f to "Awake",
        1f to "Light sleep",
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
        for (record in sessionRecords.value) {
            val formattedTime = formatter.format(record.startTime)
            val value = when (record.stage) {
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
        startTimeSleep = sessionRecords.value.first().startTime
        endTimeSleep = sessionRecords.value.last().endTime
        duration = Duration.between(startTimeSleep, endTimeSleep)
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
                .verticalScroll(rememberScrollState())
                .padding(top = 90.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your sleep",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Spacer(modifier = Modifier.height(55.dp))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                if (sessionRecords.value.isNotEmpty()) {
                    Row {
                        Column {
                            Text(
                                modifier = Modifier.padding(top = 10.dp, start = 12.dp),
                                text = "REM"
                            )
                            Text(
                                modifier = Modifier.padding(top = 150.dp, start = 12.dp),
                                text = "Light sleep"
                            )
                            Text(
                                modifier = Modifier.padding(top = 95.dp, start = 12.dp),
                                text = "Awake"
                            )
                        }
                        LineGraph(
                            modifier = Modifier
                                .padding(top = 12.dp, bottom = 12.dp),
                            data = pointsData.value,
                            onPointClick = { value: LineData ->
                                showToast(context, value.x, stageMap[value.y] ?: "Awake")
                            },
                            style = LineGraphStyle(
                                visibility = LineGraphVisibility(
                                    isYAxisLabelVisible = false,
                                    isXAxisLabelVisible = false,
                                    isCrossHairVisible = true
                                ),
                                colors = LineGraphColors(
                                    lineColor = Color.White,
                                    pointColor = Color.Transparent,
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
                    Row(modifier = Modifier.padding(top = 330.dp)) {
                        Spacer(modifier = Modifier.width(100.dp))
                        Text(text = formatter.format(startTimeSleep), color = Color.White)
                        Spacer(modifier = Modifier.width(160.dp))
                        Text(text = formatter.format(endTimeSleep), color = Color.White)
                    }
                } else {
                    Text(text = "No sleep data available.", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(55.dp))
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Your sleep duration was: $duration",
                    color = Color.White
                )
            }
        }
    }
}

fun showToast(context: Context, time: String, stage: String) {
    Toast.makeText(context, "Time: $time, Stage: $stage", Toast.LENGTH_SHORT).show()
}
