package ua.deti.pt.phoneapp.ui.screens

import android.health.connect.datatypes.SleepSessionRecord
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.common.model.Point
import ua.deti.pt.phoneapp.data.health.SleepSessionData
import ua.deti.pt.phoneapp.data.health.SleepSessionViewModel
import ua.deti.pt.phoneapp.ui.components.sleepchart.SingleLineChartWithGridLines
import ua.deti.pt.phoneapp.ui.components.sleepchart.SleepSessionRow
import java.util.UUID

@Composable
fun SleepSessionScreen(
    permissions: Set<String>,
    permissionsGranted: Boolean,
    sessionsList: List<SleepSessionData>,
    uiState: SleepSessionViewModel.UiState,
    onInsertClick: () -> Unit = {},
    onError: (Throwable?) -> Unit = {},
    onPermissionsResult: () -> Unit = {},
    onPermissionsLaunch: (Set<String>) -> Unit = {}
) {

    val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

    LaunchedEffect(uiState) {
        if (uiState is SleepSessionViewModel.UiState.Uninitialized) {
            onPermissionsResult()
        }

        if (uiState is SleepSessionViewModel.UiState.Error && errorId.value != uiState.uuid) {
            onError(uiState.exception)
            errorId.value = uiState.uuid
        }
    }

    if (uiState != SleepSessionViewModel.UiState.Uninitialized) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!permissionsGranted) {
                item {
                    Button(
                        onClick = { onPermissionsLaunch(permissions) }
                    ) {
                        Text(text = "Request permissions")
                    }
                }
            } else {
                item {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(4.dp),
                        onClick = {
                            onInsertClick()
                        }
                    ) {
                        Text(text = "Insert sleep session")
                    }
                }

                items(sessionsList) { session ->
                    SleepSessionRow(session)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun SleepScreen() {

    val sleepSessions = listOf<SleepSessionRecord>()

    val pointsData = sleepSessions.mapIndexed { index, sleepSessionRecord ->
        Point(
            x = index.toFloat(),
            y = index.toFloat(),
        )
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
