package ua.deti.pt.wearosapp.service

import android.util.Log
import androidx.concurrent.futures.await
import androidx.health.services.client.HealthServicesClient
import androidx.health.services.client.MeasureCallback
import androidx.health.services.client.data.Availability
import androidx.health.services.client.data.DataPointContainer
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.DataTypeAvailability
import androidx.health.services.client.data.DeltaDataType
import androidx.health.services.client.data.SampleDataPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.runBlocking
import ua.deti.pt.wearosapp.TAG
import javax.inject.Inject


/**
 * Manages health services, specifically focusing on the heart rate measurement.
 * Uses dependency injection to receive a HealthServicesClient instance upon creation.
 *
 * @property healthServicesClient The client interface to interact with health services
 */
class HealthServicesManager @Inject constructor(
    healthServicesClient: HealthServicesClient
) {
    private val measureClient = healthServicesClient.measureClient

    /**
     * Checks if the device supports heart rate measurements.
     * This method asynchronously retrieves the supported data types for measurements and checks if heart rate BPM is among them.
     *
     * @return True if heart rate capability is supported, false otherwise.
     */
    suspend fun hasHeartRateCapability(): Boolean {
        val capabilities = measureClient.getCapabilitiesAsync().await()
        return (DataType.HEART_RATE_BPM in capabilities.supportedDataTypesMeasure)
    }

    /**
     * Returns a cold flow. When activated, the flow will register a callback for the heart rate data
     * and start to emit messages. When the consuming coroutine is cancelled, the measure callback
     * is unregistered
     *
     * [callbackFlow] is used to bridge between a callback-based API and Kotlin flows
     */
    @ExperimentalCoroutinesApi
    fun heartRateMeasureFlow() = callbackFlow {
        val callback = object : MeasureCallback {
            override fun onAvailabilityChanged(
                dataType: DeltaDataType<*, *>,
                availability: Availability
            ) {
                // Only send back DataTypeAvailability (not LocationAvailability)
                if (availability is DataTypeAvailability) {
                    trySendBlocking(MeasureMessage.MeasureAvailability(availability))
                }
            }

            override fun onDataReceived(data: DataPointContainer) {
                // Extracts heart rate BPM data from the received data container and sends it through the flow.
                val heartRateBpm = data.getData(DataType.HEART_RATE_BPM)
                trySendBlocking(MeasureMessage.MeasureData(heartRateBpm))
            }
        }

        Log.d(TAG, "Registering for data")
        measureClient.registerMeasureCallback(DataType.HEART_RATE_BPM, callback)

        awaitClose {
            Log.d(TAG, "Unregistering for data")
            // Ensures the callback is unregistered when the flow collector cancels its subscription
            runBlocking {
                measureClient.unregisterMeasureCallbackAsync(DataType.HEART_RATE_BPM, callback)
            }
        }
    }
}

/**
 * Represents messages related to heart rate measurement status or data.
 * Used within tge HealthServicesManager to communicate measurement availability and actual points
 */
sealed class MeasureMessage {

    /**
     * Indicates the availability of heart rate measurement data.
     * Contains information about whether the data is currently available or not.
     */
    class MeasureAvailability(val availability: DataTypeAvailability) : MeasureMessage()

    /**
     * Contains actual heart rate measurement data.
     * Each instance represents a single measurement event, encapsulating the measured heart rate values.
     */
    class MeasureData(val data: List<SampleDataPoint<Double>>) : MeasureMessage()
}