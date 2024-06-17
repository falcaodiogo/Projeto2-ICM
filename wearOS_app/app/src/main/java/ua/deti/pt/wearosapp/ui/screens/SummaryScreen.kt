@file:OptIn(ExperimentalHorologistApi::class)

package ua.deti.pt.wearosapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.ItemType
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults.padding
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults.firstItemPadding
import com.google.android.horologist.compose.material.ResponsiveListHeader
import com.google.android.horologist.compose.material.Title
import ua.deti.pt.wearosapp.R
import ua.deti.pt.wearosapp.theme.WearOSAppTheme
import ua.deti.pt.wearosapp.ui.components.SummaryFormat
import ua.deti.pt.wearosapp.ui.components.formatCalories
import ua.deti.pt.wearosapp.ui.components.formatDistanceKm
import ua.deti.pt.wearosapp.ui.components.formatElapsedTime
import ua.deti.pt.wearosapp.ui.components.formatHeartRate
import ua.deti.pt.wearosapp.ui.state.SummaryScreenState
import ua.deti.pt.wearosapp.ui.viewModels.SummaryViewModel
import java.time.Duration

// End-of-workout summary screen
@Composable
fun SummaryRoute(
    onRestartClick: () -> Unit
) {
    val viewModel = hiltViewModel<SummaryViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SummaryScreen(uiState = uiState, onRestartClick = onRestartClick)
}

@Composable
fun SummaryScreen(
    uiState: SummaryScreenState,
    onRestartClick: () -> Unit
) {

    val columnState = rememberResponsiveColumnState(
        contentPadding = padding(
            first = ItemType.Text,
            last = ItemType.Chip
        )
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.yellow_background),
            contentDescription = "Summary Screen Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        ScreenScaffold(scrollState = columnState) {
            ScalingLazyColumn(columnState = columnState) {

                item {
                    ResponsiveListHeader(contentPadding = firstItemPadding()) {
                        Title(text = stringResource(id = R.string.workout_complete))
                    }
                }

                item {
                    SummaryFormat(
                        value = formatElapsedTime(
                            elapsedDuration = uiState.elapsedTime,
                            includeSeconds = true
                        ),
                        metric = stringResource(id = R.string.duration),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    SummaryFormat(
                        value = formatHeartRate(uiState.averageHeartRate),
                        metric = stringResource(id = R.string.avgHR),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    SummaryFormat(
                        value = formatDistanceKm(uiState.totalDistance),
                        metric = stringResource(id = R.string.distance),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    SummaryFormat(
                        value = formatCalories(uiState.totalCalories),
                        metric = stringResource(id = R.string.calories),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Chip(
                        label = stringResource(id = R.string.restart),
                        onClick = onRestartClick
                    )
                }
            }
        }
    }
}

@WearPreviewDevices
@Composable
fun SummaryScreenPreview() {
    WearOSAppTheme {
        SummaryScreen(
            uiState = SummaryScreenState(
                averageHeartRate = 29.30,
                totalDistance = 20291.24,
                totalCalories = 4803.67,
                elapsedTime = Duration.ofMinutes(28).plusSeconds(38)
            ),
            onRestartClick = {}
        )
    }
}



