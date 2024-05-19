package ua.deti.pt.phoneapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.ui.components.segments.Segments

@Composable
fun NotificationsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.notifications_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(modifier = Modifier.padding(24.dp).padding(top = 124.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.notifications),
                color = Color.White,
                fontSize = 36.sp,
            )
            Box(modifier = Modifier.padding(top = 16.dp)) {
                Segments()
                Text(text = "estava a fazer as tabs das notificações, mas falta primeiro conectar com a BD ou API acho")
            }
        }
    }
}