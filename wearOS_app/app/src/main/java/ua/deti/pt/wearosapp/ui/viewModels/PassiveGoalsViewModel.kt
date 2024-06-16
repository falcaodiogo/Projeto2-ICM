package ua.deti.pt.wearosapp.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.deti.pt.wearosapp.repositories.GoalsRepository
import ua.deti.pt.wearosapp.repositories.HealthServiceRepository
import java.time.Instant

/**
 * ViewModel for managing the UI state related to passive goals functionality.
 * It interacts with repositories to fetch and update data regarding health services and goals.
 */
class PassiveGoalsViewModel(
    private val healthServiceRepository: HealthServiceRepository,
    private val goalsRepository: GoalsRepository
) : ViewModel() {

    // Exposes the latest floor goal time as a LiveData stream.
    val latestFloorsTime = goalsRepository.latestFloorsGoalTime.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        Instant.EPOCH
    )

    // Exposes whether the daily steps goal has been achieved today as a LiveData stream.
    val stepsGoalAchieved = goalsRepository.dailyStepsGoalAchieved.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    // Exposes whether passive goals are enabled as a LiveData stream.
    val goalsEnabled = goalsRepository.passiveGoalsEnabled.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    init {
        viewModelScope.launch {
            val supported = healthServiceRepository.hasCapabilities()
            uiState.value = if (supported) UiState.Supported else UiState.NotSupported
        }

        viewModelScope.launch {
            goalsRepository.passiveGoalsEnabled.distinctUntilChanged().collect { enabled ->
                if (enabled) {
                    healthServiceRepository.subscribeForGoals()
                } else {
                    healthServiceRepository.unsubscribeForGoals()
                }
            }
        }
    }

    fun toggleEnabled() {
        viewModelScope.launch {
            val newEnableStatus = !goalsEnabled.value
            goalsRepository.setPassiveGoalsEnabled(newEnableStatus)
        }
    }
}

/**
 * Factory class for creating instances of PassiveGoalsViewModel.
 * Ensures that the correct dependencies are injected when the ViewModel is instantiated.
 */
class PassiveGoalsViewModelFactory(
    private val healthServicesRepository: HealthServiceRepository,
    private val goalsRepository: GoalsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PassiveGoalsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PassiveGoalsViewModel(
                healthServiceRepository = healthServicesRepository,
                goalsRepository = goalsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * Sealed class representing the different states the UI can be in regarding passive goals support.
 * - Startup: Initial state before any checks have been performed.
 * - Supported: Indicates that the health service supports passive goals.
 * - NotSupported: Indicates that the health service does not support passive goals.
 */
sealed class UiState {
    object Startup : UiState()
    object NotSupported : UiState()
    object Supported : UiState()
}