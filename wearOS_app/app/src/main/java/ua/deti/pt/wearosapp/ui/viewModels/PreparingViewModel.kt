package ua.deti.pt.wearosapp.ui.viewModels

import android.Manifest
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.deti.pt.wearosapp.repositories.HealthServiceRepository
import ua.deti.pt.wearosapp.repositories.ServiceState
import ua.deti.pt.wearosapp.ui.state.PreparingScreenState
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class PreparingViewModel @Inject constructor(
    private val healthServicesRepository: HealthServiceRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            healthServicesRepository.prepareExercise()
        }
    }

    fun startExercise() {
        healthServicesRepository.startExercise()
    }

    val uiState: StateFlow<PreparingScreenState> = healthServicesRepository.serviceState.map {
        val isTrackingInAnotherApp = healthServicesRepository.isTrackingExerciseInAnotherApp()
        val hasExerciseCapabilities = healthServicesRepository.hasExerciseCapability()
        toUiState(
            serviceState = it,
            isTrackingInAnotherApp = isTrackingInAnotherApp,
            hasExerciseCapabilities = hasExerciseCapabilities
        )
    }.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds),
        initialValue = toUiState(healthServicesRepository.serviceState.value)
    )

    private fun toUiState(
        serviceState: ServiceState,
        isTrackingInAnotherApp: Boolean = false,
        hasExerciseCapabilities: Boolean = true
    ): PreparingScreenState {
        return if (serviceState is ServiceState.Disconnected) {
            PreparingScreenState.Disconnected(serviceState, isTrackingInAnotherApp, permissions)
        } else {
            PreparingScreenState.Preparing(
                serviceState = serviceState as ServiceState.Connected,
                isTrackingInAnotherApp = isTrackingInAnotherApp,
                requiredPermissions = permissions,
                hasExerciseCapabilities = hasExerciseCapabilities,
            )
        }
    }

    companion object {
        val permissions = mutableListOf(
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACTIVITY_RECOGNITION
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                this.add(Manifest.permission.POST_NOTIFICATIONS)

            this.toList()
        }
    }

}