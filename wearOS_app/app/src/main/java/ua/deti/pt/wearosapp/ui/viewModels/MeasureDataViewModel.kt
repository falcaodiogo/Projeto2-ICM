package ua.deti.pt.wearosapp.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.health.services.client.data.DataTypeAvailability
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import ua.deti.pt.wearosapp.service.HealthServicesRepository
import ua.deti.pt.wearosapp.service.MeasureMessage

/**
 * ViewModel for handling UI logic related to measuring heart rate data.
 * Interacts with the `HealthServicesRepository` to fetch and manage heart rate data states.
 *
 * @property healthServicesRepository The repository used to interact with the health services API.
 */
class MeasureDataViewModel(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModel() {

    private val enabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val heartRate: MutableState<Double> = mutableDoubleStateOf(0.0)
    private val availability: MutableState<DataTypeAvailability> =
        mutableStateOf(DataTypeAvailability.UNKNOWN)
    private val uiState: MutableState<UiState> = mutableStateOf(UiState.Startup)

    /**
     * Initialization block that sets the initial UI state based on whether heart rate capability is supported.
     */
    init {
        viewModelScope.launch {
            val supported = healthServicesRepository.hasHeartRateCapability()
            uiState.value = if (supported) {
                UiState.Supported
            } else {
                UiState.NotSupported
            }
        }

        viewModelScope.launch {
            enabled.collect {
                if (it) {
                    healthServicesRepository.heartRateMeasureFlow()
                        .takeWhile { enabled.value }
                        .collect { measureMessage ->
                            when (measureMessage) {
                                is MeasureMessage.MeasureAvailability -> {
                                    availability.value = measureMessage.availability
                                }

                                is MeasureMessage.MeasureData -> {
                                    heartRate.value = measureMessage.data.last().value
                                }
                            }
                        }

                }
            }
        }
    }

    /**
     * Toggles the enabled state of heart rate measurement.
     * Resets the availability state if disabled.
     */
    fun toggleEnabled() {
        enabled.value = !enabled.value
        if (!enabled.value) {
            availability.value = DataTypeAvailability.UNKNOWN
        }
    }
}

/**
 * Factory class for creating instances of MeasureDataViewModel.
 * Ensures that the ViewModel is instantiated with the correct dependencies.
 */
class MeasureDataViewModelFactory(
    private val healthServicesRepository: HealthServicesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeasureDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeasureDataViewModel(
                healthServicesRepository = healthServicesRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * Sealed class representing different UI states for displaying the heart rate measurement.
 */
sealed class UiState {
    data object Startup : UiState()
    data object NotSupported : UiState()
    data object Supported : UiState()
}