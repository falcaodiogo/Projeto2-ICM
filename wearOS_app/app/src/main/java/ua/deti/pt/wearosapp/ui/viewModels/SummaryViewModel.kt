package ua.deti.pt.wearosapp.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ua.deti.pt.wearosapp.Screen
import ua.deti.pt.wearosapp.ui.state.SummaryScreenState
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState = MutableStateFlow(
        SummaryScreenState(
            averageHeartRate = savedStateHandle.get<Float>(Screen.Summary.averageHeartRateArg)!!
                .toDouble(),
            totalCalories = savedStateHandle.get<Float>(Screen.Summary.totalCaloriesArg)!!
                .toDouble(),
            totalDistance = savedStateHandle.get<Float>(Screen.Summary.totalDistanceArg)!!
                .toDouble(),
            elapsedTime = Duration.parse(savedStateHandle.get(Screen.Summary.elapsedTimeArg)!!)
        )
    )
}