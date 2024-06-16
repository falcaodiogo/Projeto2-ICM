package ua.deti.pt.phoneapp.ui.screens

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.data.notifications.NotificationHandler
import ua.deti.pt.phoneapp.ui.components.segments.Segments
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NotificationsScreen(navController: NavHostController, context: Context) {
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)
    var notifications by remember { mutableStateOf(notificationHandler.returnAllNotifications()) }

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.notifications_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .padding(24.dp)
                .padding(top = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {
            Button(modifier = Modifier.padding(top=20.dp), onClick = { notificationHandler.showSimpleNotification()
                notifications = notificationHandler.returnAllNotifications()}) {
                Text(text = "Send notification")
            }
            Column(
                modifier = Modifier.padding(bottom = 40.dp, top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = notifications.size.toString(),
                    color = Color.White,
                    fontSize = 52.sp,
                )
                Text(
                    text = stringResource(id = R.string.notifications),
                    color = Color.White,
                    fontSize = 24.sp,
                )
            }
            Column(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 1..notifications.size) {
                    Segments(
                        onClick = {},
                        title = "Notification $i",
                        description = "Message or text with notification",
                        color = Color(0xFF9fae41),
                        background = Color(0xFFe8e4cc)
                    )
                    if (i < notifications.size) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                Spacer(modifier = Modifier.size(84.dp))
            }
        }
    }
}