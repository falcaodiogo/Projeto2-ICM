package ua.deti.pt.wearosapp.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.TimeZone

// Declare a DataStore instance within the application context for storing goals-related data.
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "goals")

/**
 * Repository class responsible for managing goals-related data within the application.
 *
 * Interacts with Android's DataStore to persistently store and retrieve information about
 * daily and floor goals, including whether passive goals are enabled, the timestamp of the
 * last daily goal achievement, and the timestamp of the last floor goal achievement.
 *
 * @property context The application context used to access the DataStore instance.
 */
class GoalsRepository(private val context: Context) {

    /**
     * Companion object holding keys for storing preferences related to goals.
     */
    companion object {
        private val LATEST_DAILY_GOAL_ACHIEVED_TIME = longPreferencesKey("latest_daily_goal_time")
        private val PASSIVE_GOALS_ENABLED = booleanPreferencesKey("passive_goals_enabled")
        private val LATEST_FLOOR_GOAL_TIME = longPreferencesKey("latest_floor_goal_time")
    }

    // Flow representing whether passive goals are currently enabled.
    val passiveGoalsEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PASSIVE_GOALS_ENABLED] ?: false
    }

    /**
     * Suspend function to enable/disable passive goals.
     *
     * @param enabled Whether passive goals should be enabled.
     */
    suspend fun setPassiveGoalsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PASSIVE_GOALS_ENABLED] = enabled
        }
    }

    /**
     * Suspend function to update the timestamp of the last daily goal achievement.
     *
     * @param time Timestamp of the last daily goal achievement.
     */
    suspend fun setLatestDailyGoalAchievedTime(time: Instant) {
        context.dataStore.edit { preferences ->
            preferences[LATEST_DAILY_GOAL_ACHIEVED_TIME] = time.toEpochMilli()
        }
    }

    // Flow representing if the daily steps goal has been achieved today.
    val dailyStepsGoalAchieved: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[LATEST_DAILY_GOAL_ACHIEVED_TIME]?.let {
            val zoneId = TimeZone.getDefault().toZoneId()
            val achievedDate =
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    zoneId
                ).toLocalDate()
            achievedDate == LocalDate.now(zoneId)
        } ?: false
    }

    /**
     * Suspend function to update the timestamp of the last floor goal achievement.
     *
     * @param time Timestamp of the last floor goal achievement.
     */
    suspend fun updateLatestFloorGoalTime(time: Instant) {
        context.dataStore.edit { preferences ->
            preferences[LATEST_FLOOR_GOAL_TIME] = time.toEpochMilli()
        }
    }

    // Flow representing the timestamp of the last floor goal achievement.
    val latestFloorsGoalTime: Flow<Instant> = context.dataStore.data.map { preferences ->
        val time = preferences[LATEST_FLOOR_GOAL_TIME] ?: 0L
        Instant.ofEpochMilli(time)
    }

}