package ua.deti.pt.wearosapp.service

import androidx.health.services.client.PassiveListenerService
import androidx.health.services.client.data.DataType
import androidx.health.services.client.data.PassiveGoal
import kotlinx.coroutines.runBlocking
import ua.deti.pt.wearosapp.repositories.GoalsRepository
import java.time.Instant

/**
 * Service to receive data from Health Services.
 *
 * Passive data is delivered from Health Services to this service. Override the appropriate methods
 * in [PassiveListenerService] to receive updates for new data points, goals achieved etc.
 */
class PassiveGoalsService : PassiveListenerService() {

    private val repository = GoalsRepository(this)

    override fun onGoalCompleted(goal: PassiveGoal) {
        val time = Instant.now()

        when (goal.dataTypeCondition.dataType) {
            DataType.FLOORS_DAILY -> runBlocking {
                repository.updateLatestFloorGoalTime(time)
            }

            DataType.STEPS_DAILY -> runBlocking {
                repository.setLatestDailyGoalAchievedTime(time)
            }
        }
    }
}