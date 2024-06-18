package ua.deti.pt.phoneapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nonnull

@Entity(tableName = "EXERCISE")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long? = null,
    @Nonnull
    val exerciseType: String,
    @Nonnull
    val dayOfWeek: String,
    @Nonnull
    val userAssociatedId: Long,
    val exerciseStage: Int = 0,
)

/**
 * Stages of the exercise:
 *  0 - Not started
 *  1 - In progress
 *  2 - Completed
 */
