package ua.deti.pt.wearosapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import ua.deti.pt.wearosapp.R

/**
 * Displays an achievement and whether it has been achieved.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AchievementCard(
    isAchieved: Boolean,
    achievedText: String,
    notAchievedText: String,
    imageVector: ImageVector,
    imageDescription: String,
    achievementDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {},
        enabled = false,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = imageDescription,
                tint = if (isAchieved) Color.Green else Color.LightGray,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                val stepsText = if (isAchieved) achievedText else notAchievedText
                Text(stepsText)
                Text(text = achievementDescription, style = MaterialTheme.typography.caption3)
            }
        }
    }
}

@Preview(
    device = Devices.WEAR_OS_SMALL_ROUND,
    showSystemUi = true
)
@Composable
fun StepsChipPreview() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        AchievementCard(
            isAchieved = true,
            achievedText = stringResource(id = R.string.steps_goal_achieved),
            notAchievedText = stringResource(id = R.string.steps_goal_not_yet_achieved),
            achievementDescription = stringResource(id = R.string.steps_goals_description),
            imageVector = Icons.Default.Celebration,
            imageDescription = stringResource(id = R.string.steps_description)
        )
    }
}