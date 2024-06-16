package ua.deti.pt.wearosapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Stairs
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Text
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.ui.components.AchievementCard
import ua.deti.pt.wearosapp.ui.components.PassiveGoalsToggle
import ua.deti.pt.wearosapp.ui.components.navbar.BottomNavigationBar
import ua.deti.pt.wearosapp.ui.viewModels.PassiveGoalsViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NotificationsScreen(
    navController: NavController,
    passiveGoalsViewModel: PassiveGoalsViewModel,
    permissionState: PermissionState
) {
    val latestFloorsTime by passiveGoalsViewModel.latestFloorsTime.collectAsState()
    val stepsGoalAchieved by passiveGoalsViewModel.stepsGoalAchieved.collectAsState()
    val goalsEnabled by passiveGoalsViewModel.goalsEnabled.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.yellow_background),
            contentDescription = "Notifications Screen Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            item {
                Text(
                    text = "Start Goals",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            item {
                PassiveGoalsToggle(
                    modifier = Modifier.fillMaxWidth(),
                    checked = goalsEnabled,
                    onCheckedChange = { passiveGoalsViewModel.toggleEnabled() },
                    permissionState = permissionState
                )
            }

            item {
                AchievementCard(
                    modifier = Modifier.fillMaxWidth(),
                    isAchieved = stepsGoalAchieved,
                    achievedText = stringResource(id = R.string.steps_goal_achieved),
                    notAchievedText = stringResource(id = R.string.steps_goal_not_yet_achieved),
                    imageVector = Icons.Default.Celebration,
                    imageDescription = stringResource(id = R.string.steps_description),
                    achievementDescription = stringResource(id = R.string.steps_goals_description)
                )
            }

            item {
                val floorsText = remember(latestFloorsTime) {
                    val formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                    val zonedDateTime =
                        ZonedDateTime.ofInstant(latestFloorsTime, ZoneId.systemDefault())
                    formatter.format(zonedDateTime)
                }
                AchievementCard(
                    modifier = Modifier.fillMaxWidth(),
                    isAchieved = latestFloorsTime != Instant.EPOCH,
                    achievedText = floorsText,
                    notAchievedText = stringResource(id = R.string.waiting),
                    achievementDescription = stringResource(id = R.string.floor_goals_description),
                    imageVector = Icons.Default.Stairs,
                    imageDescription = stringResource(id = R.string.floor_description)
                )
            }

            item {
                BottomNavigationBar(navController = navController)
            }
        }
    }
}
