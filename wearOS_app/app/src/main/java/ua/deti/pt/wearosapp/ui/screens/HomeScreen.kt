package ua.deti.pt.wearosapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.components.CircularProgressBar
import ua.deti.pt.wearosapp.components.navbar.BottomNavigationBar
import ua.deti.pt.wearosapp.components.progressFlow
import ua.deti.pt.wearosapp.ui.components.HeartRateLabel
import ua.deti.pt.wearosapp.ui.viewModels.MeasureDataViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    permissionState: PermissionState,
    measureDataViewModel: MeasureDataViewModel
) {

    val heartRate by measureDataViewModel.heartRate.collectAsState()
    val availability by measureDataViewModel.availability.collectAsState()
    val enabled by measureDataViewModel.enabled.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.violet_background),
            contentDescription = "Home Screen Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            item {
                Text(
                    text = "Health",
                    modifier = Modifier.padding(top = 8.dp),
                    fontWeight = FontWeight.ExtraBold, fontSize = 18.sp
                )
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
                HeartRateLabel(
                    heartRate = heartRate,
                    availability = availability
                )
                Text(text = "3.2 km", fontSize = 16.sp)
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    onClick = {
                        if (permissionState.status.isGranted) {
                            measureDataViewModel.toggleEnabled()
                        } else {
                            permissionState.launchPermissionRequest()
                        }
                    }
                ) {
                    val buttonTextId = if (enabled) {
                        R.string.stop
                    } else {
                        R.string.start
                    }
                    Text(text = stringResource(id = buttonTextId))
                }
            }

            item {
                BottomNavigationBar(navController = navController)
            }
        }
    }
}
