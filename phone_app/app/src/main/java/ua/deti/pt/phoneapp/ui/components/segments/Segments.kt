package ua.deti.pt.phoneapp.ui.components.segments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Watch
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Segments(
    onClick: () -> Unit,
    title: String,
    description: String,
    color: Color,
    hasChevron: Boolean = false,
    exerciseStage: Int? = null
) {
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = 0.8f))
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(color)
                            .align(Alignment.CenterVertically),
                    )
                    if (description.isNotEmpty()) {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 14.dp, end = 14.dp,)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = title.toString(), color = Color.Black)
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = description.toString(), color = Color(0xFF4A4739), fontSize = 14.sp, lineHeight = 16.sp)
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 14.dp)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = title.toString(), color = Color.Black)
                        }
                    }
                }
                if (hasChevron) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Chevron right",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 16.dp)
                    )
                }
                if (exerciseStage != null) {
                    when (exerciseStage) {
                        0 -> Icon(
                            modifier = Modifier.size(48.dp).padding(end = 16.dp),
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Not started",
                            tint = Color.DarkGray,
                        )
                        1 -> Icon(
                            modifier = Modifier.size(48.dp).padding(end = 16.dp),
                            imageVector = Icons.Default.Watch,
                            contentDescription = "In progress",
                            tint = Color.DarkGray,
                        )
                        2 -> Icon(
                            modifier = Modifier.size(48.dp).padding(end = 16.dp),
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = Color.DarkGray,
                        )
                        else -> Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Error",
                            tint = Color.Red,
                            modifier = Modifier.size(48.dp).padding(end = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
