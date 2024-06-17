package ua.deti.pt.wearosapp.repositories

import android.content.Context
import androidx.health.services.client.data.LocationAvailability
import dagger.hilt.android.ActivityRetainedLifecycle
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ua.deti.pt.wearosapp.di.bindService
import ua.deti.pt.wearosapp.service.ExerciseLogger
import ua.deti.pt.wearosapp.service.ExerciseService
import ua.deti.pt.wearosapp.service.ExerciseServiceState
import javax.inject.Inject

@ActivityRetainedScoped
class HealthServiceRepository @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val exerciseClientManager: ExerciseClientManager,
    private val logger: ExerciseLogger,
    private val coroutineScope: CoroutineScope,
    lifecycle: ActivityRetainedLifecycle
) {
    private val binderConnection =
        lifecycle.bindService<ExerciseService.LocalBinder, ExerciseService>(applicationContext)

    private val exerciseServiceStateUpdates: Flow<ExerciseServiceState> =
        binderConnection.flowWhenConnected(ExerciseService.LocalBinder::exerciseServiceState)

    private var errorState: MutableStateFlow<String?> = MutableStateFlow(null)

    val serviceState: StateFlow<ServiceState> =
        exerciseServiceStateUpdates.combine(errorState) { exerciseServiceState, errorString ->
            ServiceState.Connected(exerciseServiceState.copy(error = errorString))
        }.stateIn(
            coroutineScope,
            started = SharingStarted.Eagerly,
            initialValue = ServiceState.Disconnected
        )

    suspend fun hasExerciseCapability(): Boolean = getExerciseCapabilities() != null

    private suspend fun getExerciseCapabilities() = exerciseClientManager.getExerciseCapabilities()

    suspend fun isExerciseInProgress(): Boolean =
        exerciseClientManager.exerciseClient.isExerciseInProgress()

    suspend fun isTrackingExerciseInAnotherApp(): Boolean =
        exerciseClientManager.exerciseClient.isTrackingExerciseInAnotherApp()

    fun prepareExercise() = serviceCall { prepareExercise() }

    private fun serviceCall(function: suspend ExerciseService.() -> Unit) = coroutineScope.launch {
        binderConnection.runWhenConnected {
            function(it.getService())
        }
    }

    fun startExercise() = serviceCall {
        try {
            errorState.value = null
            startExercise()
        } catch (e: Exception) {
            errorState.value = e.message
            logger.error("Error starting exercise", e.fillInStackTrace())
        }
    }

    fun pauseExercise() = serviceCall { pauseExercise() }
    fun endExercise() = serviceCall { endExercise() }
    fun resumeExercise() = serviceCall { resumeExercise() }
}

/**
 * Store exercise values in the service state. While the service is connected,
 * the values will persist.
 **/
sealed class ServiceState {
    data object Disconnected : ServiceState()

    data class Connected(
        val exerciseServiceState: ExerciseServiceState
    ) : ServiceState() {
        val locationAvailabilityState: LocationAvailability =
            exerciseServiceState.locationAvailability
    }
}
