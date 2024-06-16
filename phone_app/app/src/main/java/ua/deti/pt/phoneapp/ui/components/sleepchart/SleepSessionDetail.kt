package ua.deti.pt.phoneapp.ui.components.sleepchart

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.records.SleepSessionRecord

@Composable
fun SleepStagesDetail(sleepStages: List<SleepSessionRecord.Stage>) {
    sleepStages.forEach { stage ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(0.4f),
                text = SleepSessionRecord.STAGE_TYPE_INT_TO_STRING_MAP[stage.stage] ?: "unknown",
                textAlign = TextAlign.Start
            )
        }
    }
}
