package ua.deti.pt.phoneapp.ui.components.sleepchart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.deti.pt.phoneapp.R
import ua.deti.pt.phoneapp.data.d.SleepSessionData
import java.time.format.DateTimeFormatter

@Composable
fun SleepSessionRow(
    sessionData: SleepSessionData,
    startExpanded: Boolean = false
) {
    var expanded by remember { mutableStateOf(startExpanded) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                expanded = !expanded
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val formatter = DateTimeFormatter.ofPattern("eee, d LLL")
        val startDateTime = sessionData.startTime.atZone(sessionData.startZoneOffset)
        Text(
            modifier = Modifier
                .weight(0.4f),
            text = startDateTime.format(formatter)
        )
        if (!expanded) {
            Text(
                modifier = Modifier
                    .weight(0.4f),
                text = "Duration"
            )
        }
        IconButton(
            onClick = { expanded = !expanded }
        ) {
            val icon = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown
            Icon(icon, contentDescription = null)
        }
    }
    if (expanded) {
        SleepSessionDetailRow(labelId = R.string.sleep_start_end, item = "oi")
        SleepSessionDetailRow(
            labelId = R.string.sleep_start_end,
            item = "Hours and minutes"
        )
        SleepSessionDetailRow(labelId = R.string.sleep_start_end, item = sessionData.notes)
        if (sessionData.stages.isNotEmpty()) {
            SleepSessionDetailRow(labelId = R.string.sleep_start_end, item = "")
            SleepStagesDetail(sessionData.stages)
        }
    }
}