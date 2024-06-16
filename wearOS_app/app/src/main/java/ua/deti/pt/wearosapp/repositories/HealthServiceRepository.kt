package ua.deti.pt.wearosapp.repositories

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServices
import androidx.health.services.client.data.PassiveListenerConfig
import ua.deti.pt.wearosapp.TAG
import ua.deti.pt.wearosapp.service.PassiveGoalsService

/**
 * Entry point for [HealthServicesClient] APIs. This also provides suspend functions around
 * those APIs to enable use in coroutines.
 */
class HealthServiceRepository(context: Context) {

    private val healthServicesClient = HealthServices.getClient(context)
    private val passiveMonitoringClient = healthServicesClient.passiveMonitoringClient

    private val goals = setOf(dailyStepsGoal, floorsGoal)
    private val requiredDataTypes = goals.map { it.dataTypeCondition.dataType }.toSet()

    // Note that the dataTypes in the [PassiveListenerConfig] should contain all the data types
    // required by the specified dailyGoals.
    private val passiveListenerConfig = PassiveListenerConfig(
        dataTypes = requiredDataTypes,
        shouldUserActivityInfoBeRequested = false,
        dailyGoals = goals,
        healthEventTypes = setOf()
    )

    suspend fun hasCapabilities(): Boolean {
        val capabilities = passiveMonitoringClient.getCapabilitiesAsync().await()
        return capabilities.supportedDataTypesPassiveGoals.containsAll(requiredDataTypes)
    }

    suspend fun subscribeForGoals() {
        Log.i(TAG, "Subscribing for goals")
        passiveMonitoringClient.setPassiveListenerServiceAsync(
            PassiveGoalsService::class.java,
            passiveListenerConfig
        ).await()
    }

    suspend fun unsubscribeForGoals() {
        Log.i(TAG, "Unsubscribing for goals")
        passiveMonitoringClient.clearPassiveListenerServiceAsync().await()
    }
}