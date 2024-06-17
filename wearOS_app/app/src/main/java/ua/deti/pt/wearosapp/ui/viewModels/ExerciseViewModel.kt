package ua.deti.pt.wearosapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ua.deti.pt.wearosapp.repositories.HealthServiceRepository
import ua.deti.pt.wearosapp.repositories.ServiceState
import ua.deti.pt.wearosapp.ui.state.ExerciseScreenState
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val healthServiceRepository: HealthServiceRepository
) : ViewModel() {
    val uiState: StateFlow<ExerciseScreenState> = healthServiceRepository.serviceState.map {
        ExerciseScreenState(
            hasExerciseCapabilities = healthServiceRepository.hasExerciseCapability(),
            isTrackingAnotherExercise = healthServiceRepository.isTrackingExerciseInAnotherApp(),
            serviceState = it,
            exerciseState = (it as? ServiceState.Connected)?.exerciseServiceState
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3_000),
        healthServiceRepository.serviceState.value.let {
            ExerciseScreenState(
                true,
                false,
                it,
                (it as? ServiceState.Connected)?.exerciseServiceState
            )
        }
    )

    suspend fun isExerciseInProgress(): Boolean {
        return healthServiceRepository.isExerciseInProgress()
    }

    fun startExercise() {
        healthServiceRepository.startExercise()
    }

    fun pauseExercise() {
        healthServiceRepository.pauseExercise()
    }

    fun endExercise() {
        healthServiceRepository.endExercise()
    }

    fun resumeExercise() {
        healthServiceRepository.resumeExercise()
    }
}