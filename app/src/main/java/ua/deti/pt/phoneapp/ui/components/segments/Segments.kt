package ua.deti.pt.phoneapp.ui.components.segments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Segments() {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(Color(0xFFe8e4cc))
        ) {
            Row(modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp).fillMaxWidth()) {
                Box(
                    modifier = Modifier.size(34.dp).clip(CircleShape).background(Color(0xFF9fae41)).align(Alignment.CenterVertically)
                )
                Column(modifier = Modifier.align(Alignment.CenterVertically).padding(start = 14.dp).fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Text(text = "You walked more than 2km today!", color = Color.Black)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Click here to see more", color = Color(0xFF4A4739))
                }
            }
        }
    }
}
